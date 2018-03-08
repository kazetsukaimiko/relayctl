/*
function loadRelays() {
    var ajax = fs.ajax()
        .GET("/rest/relay")
        //.POST("http://localhost:8080/rest/index/search")
        .accept("application/json")
        .handle(200, function(xhr, request) {
          //toggleOff(".relays");
          var relays = JSON.parse(xhr.responseText);
          loadRelayDOM(relays);
          //toggleOn(".relays");
        })
        .handleOthers(function(xhr, request) {
          logger(xhr.status.toString() + " Failed to load: " + xhr.statusText + '\n' + xhr.responseText);
        })
        .json();
}
*/

function handleQueryResult(xhr, request) {
    var queryResult = JSON.parse(xhr.responseText);
    loadRelayDOM(queryResult.relays);
    loadControlDOM(queryResult.controls);
}

function loadStatus() {
    var ajax = fs.ajax()
        .GET("/rest/api")
        .accept("application/json")
        .handle(200, handleQueryResult)
        .handleOthers(function(xhr, request) {
          logger(xhr.status.toString() + " Failed to load: " + xhr.statusText + '\n' + xhr.responseText);
        })
        .json();
}

function relayByIdState(relayId, state) {
    var ajax = fs.ajax()
        .GET("/rest/api/relays/id/"+relayId+"/"+state)
        //.POST("http://localhost:8080/rest/index/search")
        .accept("application/json")
        .handle(200, handleQueryResult)
        .handleOthers(function(xhr, request) {
          logger(xhr.status.toString() + " Failed to load: " + xhr.statusText + '\n' + xhr.responseText);
        })
        .json();
}

function controlByNameState(controlName, state) {
    var ajax = fs.ajax()
        .GET("/rest/api/controls/name/"+controlName+"/"+state)
        //.POST("http://localhost:8080/rest/index/search")
        .accept("application/json")
        .handle(200,  handleQueryResult)
        .handleOthers(function(xhr, request) {
          logger(xhr.status.toString() + " Failed to load: " + xhr.statusText + '\n' + xhr.responseText);
        })
        .json();
}


function loadRelayDOM(relays) {
    for(var i=0;i<relays.length;i++) {
        getOrCreateRelay(relays[i]);
    }
}

function loadControlDOM(controls) {
    for(var i=0;i<controls.length;i++) {
        getOrCreateControl(controls[i]);
    }
}

function makeDOM(domType, name) {
  var domInstance = document.createElement(domType);
  addClass(domInstance, name);
  domInstance.id = name;
  return domInstance;
}

function makeRelaySwitch(relay) {
    var label = document.createElement("LABEL");
    addClass(label, "switch-light switch-holo");

    var checkbox = document.createElement("INPUT");
    checkbox.setAttribute("type","checkbox");
    checkbox.id = "relayctl_" + relay.id;
    checkbox.dataset["relayid"] = relay.id;
    checkbox.checked = relay.state;

    checkbox.addEventListener("click", function(evt) {
        var evt = evt || window.event;
        var target = evt.target || evt.toElement || evt.relatedTarget;
        //evt.stopPropagation();
        //evt.preventDefault();
        if (target.checked) {
            relayByIdState(relay.id, "true");
        } else {
            relayByIdState(relay.id, "false");
        }
    });

    var title = document.createElement("STRONG");
    title.textContent = relay.name;

    var spanTop = document.createElement("SPAN");
    var spanOff = document.createElement("SPAN");
    spanOff.textContent = "Off";
    var spanOn  = document.createElement("SPAN");
    spanOn.textContent = "On";
    var anchor  = document.createElement("A");

    spanTop.appendChild(spanOff);
    spanTop.appendChild(spanOn);
    spanTop.appendChild(anchor);

    label.appendChild(checkbox);
    label.appendChild(title);
    label.appendChild(spanTop);

    return label;
}

function makeControlMenuOption(control, stateName) {
    var option = document.createElement("option");
    option.textContent = stateName;
    option.setAttribute("value", stateName);
    if (control.activeState == stateName) {
        console.log("Default state: " + stateName);
        option.setAttribute("selected", true);
    }
    return option;
}

function makeControlMenu(control) {
    var label = document.createElement("LABEL");
    addClass(label, "switch-light switch-holo");

    var select = document.createElement("select");
    select.setAttribute("type","select");
    select.id = "controlctl_" + idFriendlyName(control.name);

    for (var i=0;i<control.availableStates.length;i++) {
        select.appendChild(makeControlMenuOption(control, control.availableStates[i]))
    }

    select.addEventListener("change", function(evt) {
        var evt = evt || window.event;
        var target = evt.target || evt.toElement || evt.relatedTarget;
        controlByNameState(control.name, target.value);
    });

    var title = document.createElement("STRONG");
    title.textContent = control.name;

    label.appendChild(title);
    label.appendChild(select);


    return label;
}

function getOrCreateRelay(relay) {
    console.log("Loading relay: " + relay.id);
    var relayDOM = document.getElementById("relay_"+relay.id);
    if (relayDOM == null) {
        relayDOM = makeDOM("div", "relay_"+relay.id);
        addClass(relayDOM, "relay");
        relayDOM.appendChild(makeRelaySwitch(relay));
        document.getElementById("relays").appendChild(relayDOM);
    } else {
        document.allElements("#relayctl_"+relay.id, function(elem) {
            elem.checked = relay.state;
        });
    }
    return relayDOM;
}

function getOrCreateControl(control) {
    console.log("Loading control: " + control.name);
    var controlDOM = document.getElementById("control_"+idFriendlyName(control.name));
    if (controlDOM == null) {
        controlDOM = makeDOM("div", "control_"+idFriendlyName(control.name));
        addClass(controlDOM, "control");
        controlDOM.appendChild(makeControlMenu(control));
        document.getElementById("controls").appendChild(controlDOM);
    } else {
        document.allElements("#controlctl_"+idFriendlyName(control.name), function(elem) {
            elem.value = control.activeState;
        });
    }
    return controlDOM;
}

function idFriendlyName(name) {
    return name.replace(/\s+/g , "_");
}


window.onload = function() {
  loadStatus();
};