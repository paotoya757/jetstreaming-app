<%-- 
    Document   : app-page
    Created on : 11/10/2015, 08:05:27 PM
    Author     : paotoyav
--%>

<%@page import="com.mycompany.jetstreaming.labredes.Utils"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!doctype html>
<!--Conditionals for IE8-9 Support-->
<!--[if IE]><html lang="en" class="ie"><![endif]-->

<head>
    <meta charset="utf-8">

    <title>Stream it up</title>

    <!--SEO Meta Tags-->
    <meta name="description" content="Responsive Multipurpose HTML5 Template" />
    <meta name="keywords" content="responsive html5 template, multipurpose, blog, portfolio, e-commerce, bootstrap 3, css, jquery, flat, modern" />
    <meta name="author" content="8Guild" />
    <!--Mobile Specific Meta Tag-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <!--Favicon-->
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    <link rel="icon" href="favicon.ico" type="image/x-icon">
    <!--Master Slider Styles-->
    <link href="masterslider/style/masterslider.css" rel="stylesheet" media="screen">
    <!--Kedavra Stylesheet-->
    <link href="css/styles.css" rel="stylesheet" media="screen">
    <!--Kedavra Color Scheme-->
    <link class="color-scheme" href="css/colors/color-2f8cea.css" rel="stylesheet" media="screen">
    <!--Google Maps API-->
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA5DLwPPVAz88_k0yO2nmFe7T9k1urQs84"></script>
    <!--Modernizr-->
    <script src="js/libs/modernizr.custom.js"></script>
    <!--Adding Media Queries Support for IE8-->
    <!--[if lt IE 9]>
      <script src="js/plugins/respond.js"></script>
    <![endif]-->

</head>

<!--Add "parallax" class if you have elements on the page with parallax backgrounds. "fadeIn" class makes content fades in on loading-->

<div class="panel panel-primary pull-left">
    <div class="panel static panel-info">
        <div class="panel-heading"><h4 class="panel-title"><%= SecurityUtils.getSubject().getPrincipal().toString()%></h4></div>
    </div>
</div>

<!--Layout-->
<div class="site-layout"><!--If you add class "boxed" to .site-layout it wraps the whole document in a box, than you can simply add pattern background to body or leave it white. Please note in a "boxed" mode header doesn't stick to the top.-->



    <div style="background-image:url(https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRdjIc-Jvk7eqz0NWxDCXzFeOvH7l8IrSMWl0kFnxPKS64ajBWYEg);" class="container space-top gray-bg">
        <div class="">
            <h1></h1>
        </div>
    </div>

    <!--Page Content--><!-- InstanceBeginEditable name="content" -->


    <!--Page Content--><!-- InstanceBeginEditable name="content" -->
    <div class="page" >

        <!--Single Post-->
        <section  class="container double-space-top">
            <div class="row">

                <!--Content-->
                <section class="" >

                    <h2 class="post-title">Video Post</h2>

                    <div class="embed-responsive embed-responsive-16by9 space-bottom">
                        <iframe width="420" height="315" src="https://www.youtube.com/embed/VgUqcBuibF0" frameborder="0" allowfullscreen></iframe>

                </section><!--Content Close-->

            </div>

            <section class="bg-warning">
                <div class="container">   
                    <!--Logo Carousel-->
                    <h3 class="heading center">PLAYLIST<span></span></h3>
                    <div class="logo-carousel">
                        <%
                            List<String> playlist = (List<String>) request.getAttribute("playlist");
                            List<String> thumbNails = (List<String>) request.getAttribute("thumbNails");
                            if (playlist != null && !playlist.isEmpty()) {
                                int i = 0;
                                for (String videoPath : playlist) {
                                    String thmbPath = thumbNails.get(i);
                        %>
                        <a href="<%= videoPath%>" > <img class="center" src="<%= thmbPath%>" alt="0<%=i + 1%>"/>  <h4 class="text-center"><%= videoPath.split("-EOL-")[1].replaceAll(".mp4", "")%></h4>  </a>
                            <%          i++;
                                    }
                                }
                            %>
                    </div>
                </div>


            </section>
        </section><!--Single Post Content Close-->
    </div>   
    <!--Page Content Close-->



    <div style="background-image:url(img/index-bg-pic.jpg);" class="specialty-page-bg"></div>
</div>
</div><!--Layout Close-->

<!--Page Content--><!-- InstanceBeginEditable name="content" -->
<div class="page">

    <!--User Profile-->
    <section class="page-block user-account double-space-bottom">
        <div class="container">
            <div class="panel panel-success pull-right">
                <div class="panel static panel-success">
                    <div class="panel-heading"><h4> <%= request.getAttribute("message") != null ? request.getAttribute("message").toString() : ""%></h4></div>
                </div>
            </div>
            <div class="row">
                <!--Content-->
                <div class="bg-success col-md-10">
                    <div class="tab-content">

                        <!--Account Settings-->
                        <div class="tab-pane fade in active" id="account">
                            <h3 class="heading">Upload a new video<span></span></h3>

                            <form method="post" class="account-settings" enctype="multipart/form-data" action="upload">
                                <div class="row">
                                    <div class="col-md-7 space-bottom text-center">
                                        <fieldset>
                                            <div class="form-group">
                                                <label for="file-path"></label>
                                                <input class="form-control" type="file" name="file-path" id="file-path" required>
                                            </div>
                                        </fieldset>
                                    </div>

                                </div>
                                <div class="row space-top">
                                    <div class="col-md-6 col-sm-6">
                                        <div class="text-center">
                                            <input class="btn btn-md btn-primary" type="submit" value="Upload">
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </section><!--User Profile Close-->

</div>
<!--Page Content Close-->

</div>
</div><!--Off-Canvas Close-->


<!--Javascript (jQuery) Libraries and Plugins-->
<script src="js/libs/jquery-1.11.1.min.js"></script>
<script src="js/libs/jquery.easing.min.js"></script>
<script src="js/plugins/bootstrap.min.js"></script>
<script src="js/plugins/smoothscroll.js"></script>
<script src="js/plugins/utilities.js"></script>
<script src="js/plugins/foundation.min.js"></script>
<script src="js/plugins/jquery.placeholder.js"></script>
<script src="js/plugins/icheck.min.js"></script>
<script src="js/plugins/jquery.validate.min.js"></script>
<script src="js/plugins/waypoints.min.js"></script>
<script src="js/plugins/isotope.min.js"></script>
<script src="js/plugins/masterslider.min.js"></script>
<script src="js/plugins/owl.carousel.min.js"></script>
<script src="js/plugins/lightGallery.min.js"></script>
<script src="js/plugins/jq  uery.stellar.min.js"></script>
<script src="js/plugins/jquery.parallax.min.js"></script>
<script src="js/plugins/chart.min.js"></script>
<script src="js/plugins/jquery-numerator.js"></script>
<script src="js/plugins/jquery.countdown.min.js"></script>
<script src="js/plugins/jquery.easypiechart.min.js"></script>
<script src="js/plugins/jquery.nouislider.min.js"></script>
<script src="js/plugins/card.min.js"></script>
<script src="js/scripts.js"></script>

<!--Body Close-->

</html>