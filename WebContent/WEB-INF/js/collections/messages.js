//	js/collections/messages.js

var app = app || {};

var MessageList = Backbone.Collection.extend({
	model: app.UserPost,
	localStorage: new Backbone.LocalStorage('message-backbone'),
});

app.Messages = new MessageList();

