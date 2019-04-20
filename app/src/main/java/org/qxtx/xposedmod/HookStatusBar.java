package org.qxtx.xposedmod;

import android.widget.TextView;
import java.util.Calendar;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @CreateDate 2019/04/19 14:04.
 * @Author QXTX-GOSPELL
 */

public class HookStatusBar implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        String pkgName = lpparam.packageName;
//        XposedBridge.log("Run: " + pkgName);
        if (pkgName.equals("com.android.systemui")) {
            XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.policy.Clock",
                    lpparam.classLoader, "updateClock",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            TextView clock = (TextView)param.thisObject;
                            Calendar calendar = Calendar.getInstance();
                            StringBuilder text = new StringBuilder();
                            text.append(calendar.get(Calendar.MONTH) + 1)
                                    .append("/")
                                    .append(calendar.get(Calendar.DAY_OF_MONTH))
                                    .append(" ")
                                    .append(clock.getText())
                                    .append(" ");

                            clock.setText(text.toString());
                        }
                    }
            );
        }
    }
}
