<link rel="stylesheet" type="text/css" href="../../resources/css/smoothness/jquery-ui-1.10.4.custom.css" />
<link rel="stylesheet" type="text/css" href="../../resources/css/happy-marvin.css" />
<link rel="stylesheet" type="text/css" href="../../resources/css/smart_wizard.css" />

<script type="text/javascript" src="../../resources/js/jquery/jquery-1.10.2.js" ></script>
<script type="text/javascript" src="../../resources/js/jquery/jquery-ui-1.10.4.custom.js" ></script>
<script type="text/javascript" src="../../resources/js/smartWizard/jquery.smartWizard-2.0.js" ></script>
   
<script type="text/javascript">
   var jq = jQuery.noConflict();
</script>
 
<script type="text/javascript">
   jq(document).ready(function() {
      // Smart Wizard   
      jq('#div_wizard').smartWizard();
      
      function onFinishCallback(){
         jq('#div_wizard').smartWizard('showMessage','Finish Clicked');
      }

      // displaying a modal Loading... window
      jq("#div_loading_modal_dialog").dialog({
          height: 70,
          modal: true
      });
      jq("#div_loading_modal_dialog").closest('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
      jq("#div_progressBar").progressbar({
         value: false
      });
      
      // loading the data
      jq.post("create/loadData",
              function(data) {
                 jq("#div_loading_modal_dialog").dialog("close");
              });
   });

   jq(document).ajaxError(function (e, xhr, settings, exception) {
      jq("#div_loading_modal_dialog").dialog("close");
      var jsonExceptionResponse = jQuery.parseJSON(xhr.responseText);
      jq("#div_error_modal_dialog").append(jsonExceptionResponse.message);
      jq("#div_error_modal_dialog").dialog({
         height: 140,
         modal: true
      });
   });
</script>


<div id="div_wizard" class="swMain">
   <!-- building the headers of the steps -->
   <ul>
      <li>
         <a href="#step-1">
            <span class="stepNumber">1</span>
            <span class="stepDesc">Type<br>
               <small>Choose a type!</small>
            </span>
         </a>
      </li>
      <li>
         <a href="#step-2">
            <span class="stepNumber">2</span>
            <span class="stepDesc">Template<br>
               <small>Choose a template!</small>
            </span>
         </a>
      </li>
   </ul>
   
   <!-- building the bodies of the steps -->
   <div id="step-1">
      <h2 class="StepTitle">Type list</h2>
      valami list
   </div>
   <div id="step-2">
      <h2 class="StepTitle">Template list</h2>
      valami list
   </div>

</div>

<div id="div_error_modal_dialog" title="Error happened!"></div>

<div id="div_loading_modal_dialog" title="Loading...">
   <div id="div_progressBar"/>
</div>