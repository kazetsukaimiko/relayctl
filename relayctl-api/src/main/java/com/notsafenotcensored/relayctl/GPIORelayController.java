package com.notsafenotcensored.relayctl;

import com.notsafenotcensored.relayctl.config.Configuration;
import com.notsafenotcensored.relayctl.relay.BaseController;
import com.notsafenotcensored.relayctl.relay.provider.GPIORelayProvider;

public class GPIORelayController extends BaseController {

    public GPIORelayController(Configuration configuration) {
        super(GPIORelayProvider.load(configuration.getRelays()));
    }

}
