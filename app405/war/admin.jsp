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
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
      <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
                    <link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
                                   <link rel="shortcut icon" href="../assets/ico/favicon.png">
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
          <a class="brand" href="" onclick="return false;">Admin</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="active"><a href="/">Home</a></li>
              <li><a href="${logoutUrl}">Logout</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">

      <h1>Admin Functions</h1>
      
      <textarea id="admin-message"></textarea>
      
      <br>
      
      <button id="save-message-btn">Save Message</button>

    </div> <!-- /container -->

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    
    <script>
      window.app = {
        loginUrl: '${loginUrl}'
      };
      
      app.getAdminMsg = function() {
        $.ajax({
          url: 'get-admin-message',
          dataType: 'json',
          type: 'POST',
          headers : { "cache-control": "no-cache" }  // see http://stackoverflow.com/questions/12506897/is-safari-on-ios-6-caching-ajax-results
        })
        .done(function(data) {
          if (data.msg) {
            $('#admin-message').html(data.msg);
          } else if (data.login) {
            location.replace(app.loginUrl);
          } else {
            $('#admin-message').html('ERROR');
          }
        })
        .fail(function(jqXHR, textStatus) {
          $('#admin-message').html(textStatus);
        });
      };
      
      app.saveAdminMsg = function() {
        // Extract the csrf token.
        var csrfStart = document.cookie.indexOf('csrf=') + 5;
        if (csrfStart === -1) {
          alert('csrf cookie not found');
          return;
        }
        var csrfEnd = document.cookie.indexOf(csrfStart, ';');
        if (csrfEnd === -1) {
          csrfEnd = document.cookie.length;
        }
        var csrfCookie = document.cookie.substring(csrfStart, csrfEnd);
        $.ajax({
          url: 'set-admin-message',
          cache: false,
          dataType: 'json',
          type: 'POST',
          data: {
            text: $('#admin-message').val(),
            csrf: csrfCookie
          }
        })
        .done(function(data) {
          if (data.error) {
            alert(data.error);
          } else if (data.login) {
            alert("Save unsuccessful.");
            location.replace(data.login);
          } else {
            alert('All good.');
          }
        })
        .fail(function(jqXHR, textStatus) {
          alert(textStatus);
        });
      };
      
      $(function() {
        app.getAdminMsg();        
        $('#save-message-btn').click(function() {
          app.saveAdminMsg();
        });
      });
    </script>
    
  </body>
</html>
