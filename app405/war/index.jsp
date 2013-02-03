<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>App 405</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="css/bootstrap.css" rel="stylesheet">
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>
    <link href="css/bootstrap-responsive.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="img/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="img/apple-touch-icon-114-precomposed.png">
      <link rel="apple-touch-icon-precomposed" sizes="72x72" href="img/apple-touch-icon-72-precomposed.png">
                    <link rel="apple-touch-icon-precomposed" href="img/apple-touch-icon-57-precomposed.png">
                                   <link rel="shortcut icon" href="img/favicon.png">
  </head>

  <body>

    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="">App 405</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="active"><a id="homeLink"    href="#">Home</a>    </li>
              <li               ><a id="aboutLink"   href="#">About</a>   </li>
              <li               ><a id="contactLink" href="#">Contact</a> </li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">

      <div id="home">
        <h1>Home</h1>
        <p id="admin-message"></p>
      </div>

      <div id="about" style="display: none">
        <h1>About</h1>
        <p>
           This site is an example application for
           <a href="https://sites.google.com/site/serverprogramming/">CSE 405</a>.
        </p>
      </div>

      <div id="contact" style="display: none">
        <h1>Contact</h1>
        <p><a href="http://cse.csusb.edu/turner/">David Turner</a></p>
      </div>

    </div> <!-- /container -->

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    
    <script>
      window.app = {};

      app.getAdminMsg = function() {
        $.ajax({
          url: 'get-admin-message',
          cache: false,
          dataType: 'json',
          type: 'POST'
        })
        .done(function(data) {
          if (data.msg) {
            $('#admin-message').html(data.msg);
          } else {
            $('#admin-message').html('ERROR');
          }
        })
        .fail(function(jqXHR, textStatus) {
          $('#admin-message').html(textStatus);
        });
      }
 
      app.screen = function(id) {
        app.$screenLink.parent().removeClass('active');
        app.$screen.fadeOut(100, function() {
          app.$screen.fadeIn(200);
        });
        app.$screenLink = $('#' + id + 'Link');
        app.$screenLink.parent().addClass('active');
        app.$screen = $('#' + id);
      };

      $(function() {
        app.$screen     = $('#home');
        app.$screenLink = $('#homeLink');
        app.getAdminMsg();
        $('#homeLink')    .click(function() { app.screen('home');    return false; } );
        $('#aboutLink')   .click(function() { app.screen('about');   return false; } );
        $('#contactLink') .click(function() { app.screen('contact'); return false; } );
      });
    </script>
    
  </body>
</html>
