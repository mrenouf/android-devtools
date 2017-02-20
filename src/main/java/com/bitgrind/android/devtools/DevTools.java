package com.bitgrind.android.devtools;

/**
 * Created by mrenouf on 2/18/17.
 */
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = DevToolsModule.class)
interface DevTools {

    DevToolsController devToolsController();

}
