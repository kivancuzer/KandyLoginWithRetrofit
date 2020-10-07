package com.example.kandyloginwithretrofit;

import android.content.Context;

import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.utilities.services.ServiceInfo;
import com.rbbn.cpaas.mobile.utilities.services.ServiceType;

import java.util.ArrayList;
import java.util.List;

public class CPaaSManager {

    public static CPaaS cpaas;

    /**
     * Singleton - Lazy Initialization
     *
     * @param context The context for CPaaS
     * @return instance of the CPaaS
     */
    static CPaaS getCpaas(Context context) {
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
