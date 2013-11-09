var argscheck = require('cordova/argscheck'),
    utils = require('cordova/utils'),
    exec = require('cordova/exec');

// Returns default params, overrides if provided with values
function parseParameters(options) {
    var opt = {
        argument: "default"
    };

    if (options) {
        if (options.argument !== undefined) {
            opt.argument = options.argument;
        }
    }

    return opt;
}

var backgroundgpssend = {
    /**
     * Hello, world!
     *
     * @param options    the options for helloWorld
     */
    helloWorld: function(onSuccess, onError, options) {
        options = parseParameters(options);
        exec(onSuccess, onError, "BackgroundGpsSend", "helloWorld", [options.argument]);
    },

    startService: function (onSuccess, onError, options) {
        options = parseParameters(options);
        exec(onSuccess, onError, "BackgroundGpsSend", "startService", [options.argument]);
    },

    stopService: function (onSuccess, onError, options) {
        options = parseParameters(options);
        exec(onSuccess, onError, "BackgroundGpsSend", "stopService", [options.argument]);
    }
};

module.exports = backgroundgpssend;
