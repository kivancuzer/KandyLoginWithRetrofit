package com.example.kandyloginwithretrofit;

import android.content.Context;

import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.utilities.services.ServiceInfo;
import com.rbbn.cpaas.mobile.utilities.services.ServiceType;

import java.util.ArrayList;
import java.util.List;

public class CPaaSManager {

    private static CPaaS cpaas;

    /**
     * Singleton - Lazy Initialization
     *
     * @return instance of the CPaaS
     */
    public static CPaaS getCpaas() {
        if (cpaas == null) {
            List<ServiceInfo> services = new ArrayList<>();
            services.add(new ServiceInfo(ServiceType.SMS, true));
            services.add(new ServiceInfo(ServiceType.CALL, true));
            services.add(new ServiceInfo(ServiceType.ADDRESSBOOK, true));
            services.add(new ServiceInfo(ServiceType.CHAT, true));
            cpaas = new CPaaS(services);
        }
        return cpaas;
    }

}
