AUI.add(
	'liferay-ddm-form-renderer-field-events',
	function(A) {
		var FieldEventsSupport = function() {
		};

		FieldEventsSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._eventHandlers.push(
					instance.after(instance._afterEventsRender, instance, 'render')
				);

				instance._domEvents = [];

				instance._bindEvents();
			},

			bindContainerEvent: function(eventName, callback, selector) {
				var instance = this;

				var container = instance.get('container');

				var query = selector;

				if (query.call) {
					query = query.call(instance);
				}

				var handler = container.delegate(eventName, A.bind(callback, instance), query);

				instance._domEvents.push(
					{
						callback: callback,
						handler: handler,
						name: eventName,
						selector: selector
					}
				);

				return handler;
			},

			bindInputEvent: function(eventName, callback) {
				var instance = this;

				return instance.bindContainerEvent(eventName, callback, instance.getInputSelector);
			},

			_afterEventsRender: function() {
				var instance = this;

				var events = [];

				while (instance._domEvents.length > 0) {
					var event = instance._domEvents.shift();

					event.handler.detach();

					events.push(event);
				}

				events.forEach(
					function(event) {
						instance.bindContainerEvent(event.name, event.callback, event.selector);
					}
				);
			},

			_bindEvents: function() {
				var instance = this;

				instance.bindInputEvent('blur', instance._onInputBlur);
				instance.bindInputEvent('change', instance._onInputChange);
			},

			_onInputBlur: function(event) {
				var instance = this;

				instance.fire(
					'blur',
					{
						domEvent: event,
						field: instance
					}
				);
			},

			_onInputChange: function(event) {
				var instance = this;

				instance.fire(
					'valueChanged',
					{
						domEvent: event,
						field: instance,
						value: instance.getValue()
					}
				);
			}
		};

		Liferay.namespace('DDM.Renderer').FieldEventsSupport = FieldEventsSupport;
	},
	'',
	{
		requires: []
	}
);