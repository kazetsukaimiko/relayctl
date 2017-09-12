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

function relayByIdState(relayId, state) {
    var ajax = fs.ajax()
        .GET("/rest/relay/id/"+relayId+"/"+state)
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

function loadRelayDOM(relays) {
    for(var i=0;i<relays.length;i++) {
        getOrCreateRelay(relays[i]);
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

function getOrCreateRelay(relay) {
    console.log("Loading relay: " + relay.id);
    var relayDOM = document.getElementById("relay_"+relay.id);
    if (relayDOM == null) {
        relayDOM = makeDOM("div", "relay_"+relay.id);
        addClass(relayDOM, "relay");
        relayDOM.appendChild(makeRelaySwitch(relay));
        document.getElementById("relays").appendChild(relayDOM);
    }
    document.allElements("#relayctl_"+relay.id, function(elem) {
        elem.checked = relay.state;
    });
    return relayDOM;
}



window.onload = function() {
  loadRelays();
};