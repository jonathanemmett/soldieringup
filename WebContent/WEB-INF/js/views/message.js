//	js/views/message.js
(function($){

var posts = [{
	title: "No Title",
	postImage: "http://placehold.it/260x180",
	intro: "No Intro",
	author: "No Author",
	publishAt: "No Date",
	comments: "0 Comments",
	tags: "No Tags"
}, {
	title: "Something",
	postImage: "http://placehold.it/260x180",
	intro: "No Intro",
	author: "No Author",
	publishAt: "No Date",
	comments: "2 Comments",
	tags: "No Tags"
}];

var Post = Backbone.Model.extend({
	defaults: {
		title: "No Title",
		postImage: "http://placehold.it/260x180",
		intro: "No Intro",
		author: "No Author",
		publishAt: "No Date",
		comments: "3 Comments",
		tags: "No Tags"
	}
});

var PostComment = Backbone.Model.extend({

});

var AllPosts = Backbone.Collection.extend({
	model: Post
});
	
var UserView = Backbone.View.extend({
	el: '#message-response',
	template: $('#message-template').html(),
	render: function() {
		var tmpl = _.template(this.template);
		this.$el.append(tmpl(this.model.toJSON()));
		return this;
	}
});

var PostCommentView = Backbone.View.extend({
	el: 'div.response',
	events: {
		"click a.close": "close"
	},

	template: $('#respond-template').html(),
	render: function() {
		this.$el.html(this.template);
		return this;
	},

	close: function(e) {
		var that = this;
		e.preventDefault();
		that.$el.empty();

	}
});

var AllPostsView = Backbone.View.extend({
	el: $('#message-response'),
	events: {
		"click a.reply": "renderReply"
	},

	initialize: function() {
		this.collection = new AllPosts(posts);
		this.render();
	},

	render: function() {
		var that = this;
		_.each(this.collection.models, function(item) {
			that.renderPost(item);
		}, this);
	},

	renderPost: function(item) {
		var userView = new UserView({
			model: item
		});

		this.$el.append(userView.render().el);
	},

	renderReply: function(e) {
		e.preventDefault();
		var postComment = new PostCommentView({
			model: PostComment
		});

		postComment.render();
	}
});

	var allpostsView = new AllPostsView();

})(jQuery);