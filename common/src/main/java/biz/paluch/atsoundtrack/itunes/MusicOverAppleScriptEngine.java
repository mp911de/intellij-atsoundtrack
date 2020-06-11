/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package biz.paluch.atsoundtrack.itunes;

import biz.paluch.atsoundtrack.applescript.AppleScriptEngine;
import biz.paluch.atsoundtrack.logging.InternalLogger;
import biz.paluch.atsoundtrack.logging.InternalLoggerFactory;
import biz.paluch.atsoundtrack.settings.AtSoundtrackSettings;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * @author Mark Paluch
 * @since 1.3
 */
public class MusicOverAppleScriptEngine extends AbstractITunesAppleScriptProvider {

    private static final InternalLogger log = InternalLoggerFactory
            .getLogger(MusicOverAppleScriptEngine.class);
    private ScriptEngine scriptEngine;

    public MusicOverAppleScriptEngine() {
        super("Music");
    }

    @Override
    public boolean isApplicable(AtSoundtrackSettings atSoundtrackSettings) {
        return atSoundtrackSettings.isITunes() && AppleScriptEngine
                .isAvailable() && isRunning();
    }

    private ScriptEngine getScriptEngine() {

        if (this.scriptEngine == null) {
            this.scriptEngine = AppleScriptEngine.createScriptEngine();
        }
        return this.scriptEngine;
    }

    @Override
    protected String eval(String code) {
        ScriptEngine engine = getScriptEngine();
        try {
            Object o = engine.eval(code);
            if (o == null) {
                return null;
            }
            return o.toString();
        } catch (ScriptException e) {
            log.warn(e.getMessage(), e);
            return null;
        }
    }
}
