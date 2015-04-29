<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
  <title>豆瓣同城 - 音乐 - 北京 | Just a iScroll Demo</title>
  <link rel="stylesheet" href="${resourcesPath }assets/css/amazeui.css"/>
  <style>
    html,
    body,
    .page {
      height: 100%;
    }

    #wrapper {
      position: absolute;
      top: 49px;
      bottom: 0;
      overflow: hidden;
      margin: 0;
      width: 100%;
      padding: 0 8px;
      background-color: #f8f8f8;
    }

    .am-list {
      margin: 0;
    }

    .am-list > li {
      background: none;
      box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.8);
    }

    .pull-action {
      text-align: center;
      height: 45px;
      line-height: 45px;
      color: #999;
    }

    .pull-action .am-icon-spin {
      display: none;
    }

    .pull-action.loading .am-icon-spin {
      display: block;
    }

    .pull-action.loading .pull-label {
      display: none;
    }
  </style>
</head>
<body>
<div class="page">
  <header data-am-widget="header" class="am-header am-header-default">
    <h1 class="am-header-title">
      同城 - 音乐
    </h1>
  </header>

  <div id="wrapper" data-am-widget="list_news"
       class="am-list-news am-list-news-default">
    <div class="am-list-news-bd">
      <div class="pull-action loading" id="pull-down">
        <span class="am-icon-arrow-down pull-label"
              id="pull-down-label"> 下拉刷新</span>
        <span class="am-icon-spinner am-icon-spin"></span>
      </div>
      <ul class="am-list" id="events-list">
        <li class="am-list-item-desced">
          <div class="am-list-item-text">
            正在加载内容...
          </div>
        </li>
      </ul>
      <div class="pull-action" id="pull-up">
        <span class="am-icon-arrow-down pull-label"
              id="pull-up-label"> 上拉加载更多</span>
        <span class="am-icon-spinner am-icon-spin"></span>
      </div>
    </div>
  </div>
</div>
<script src="${resourcesPath }assets/js/jquery.min.js"></script>
<script src="${resourcesPath }assets/js/handlebars.min.js"></script>
<script type="text/x-handlebars-template" id="tpi-list-item">
  {{#each this}}
  <li class="am-list-item-desced" data-id="{{id}}">
    <a href="{{alt}}" class="am-list-item-hd" target="_blank">{{title}}</a>

    <div class="am-list-item-text">{{content}}</div>
  </li>
  {{/each}}
</script>
<script src="${resourcesPath }assets/js/amazeui.min.js"></script>
<script>
  (function($) {
    var EventsList = function(element, options) {
      var $main = $('#wrapper');
      var $list = $main.find('#events-list');
      var $pullDown = $main.find('#pull-down');
      var $pullDownLabel = $main.find('#pull-down-label');
      var $pullUp = $main.find('#pull-up');
      var topOffset = -$pullDown.outerHeight();

      this.compiler = Handlebars.compile($('#tpi-list-item')());
      this.prev = this.next = this.start = options.params.start;
      this.total = null;

      this.getURL = function(params) {
        var queries = ['callback=?'];
        for (var key in  params) {
          if (key !== 'start') {
            queries.push(key + '=' + params[key]);
          }
        }
        queries.push('start=');
        return options.api + '?' + queries.join('&');
      };

      this.renderList = function(start, type) {
        var _this = this;
        var $el = $pullDown;

        if (type === 'load') {
          $el = $pullUp;
        }

        $.getJSON(this.URL + start).then(function(data) {
          console.log(data);
          _this.total = data.total;
          var html = _this.compiler(data.events);
          if (type === 'refresh') {
            $list.children('li').first().before(html);
          } else if (type === 'load') {
            $list.append(html);
          } else {
            $list(html);
          }

          // refresh iScroll
          setTimeout(function() {
            _this.iScroll.refresh();
          }, 100);
        }, function() {
          console.log('Error...')
        }).always(function() {
          _this.resetLoading($el);
          if (type !== 'load') {
            _this.iScroll.scrollTo(0, topOffset, 800, $.AMUI.iScroll.utils.circular);
          }
        });
      };

      this.setLoading = function($el) {
        $el.addClass('loading');
      };

      this.resetLoading = function($el) {
        $el.removeClass('loading');
      };

      this.init = function() {
        var myScroll = this.iScroll = new $.AMUI.iScroll('#wrapper', {});
        // myScroll.scrollTo(0, topOffset);
        var _this = this;
        var pullFormTop = false;
        var pullStart;

        this.URL = this.getURL(options.params);
        this.renderList(options.params.start);

        myScroll.on('scrollStart', function() {
          if (this.y >= topOffset) {
            pullFormTop = true;
          }

          pullStart = this.y;
          // console.log(this);
        });

        myScroll.on('scrollEnd', function() {
          if (pullFormTop && this.directionY === -1) {
            _this.handlePullDown();
          }
          pullFormTop = false;

          // pull up to load more
          if (pullStart === this.y && (this.directionY === 1)) {
            _this.handlePullUp();
          }
        });
      };

      this.handlePullDown = function() {
        console.log('handle pull down');
        if (this.prev > 0) {
          this.setLoading($pullDown);
          this.prev -= options.params.count;
          this.renderList(this.prev, 'refresh');
        } else {
          console.log('别刷了，没有了');
        }
      };

      this.handlePullUp = function() {
        console.log('handle pull up');
        if (this.next < this.total) {
          this.setLoading($pullUp);
          this.next += options.params.count;
          this.renderList(this.next, 'load');
        } else {
          console.log(this.next);
          // this.iScroll.scrollTo(0, topOffset);
        }
      }
    };

    $(function() {
      var app = new EventsList(null, {
        api: 'https://api.douban.com/v2/event/list',
        params: {
          start: 100,
          type: 'music',
          count: 10,
          loc: 'beijing'
        }
      });
      app.init();
    });

    document.addEventListener('touchmove', function(e) {
      e.preventDefault();
    }, false);
  })(window.jQuery);
</script>
</body>
</html>
<!--http://pnc.co.il/dev/iscroll-5-pull-to-refresh-and-infinite-demo-->