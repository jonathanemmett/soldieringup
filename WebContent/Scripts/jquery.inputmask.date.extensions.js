/*
Input Mask plugin extensions
http://github.com/RobinHerbots/jquery.inputmask
Copyright (c) 2010 - 2012 Robin Herbots
Licensed under the MIT license (http://www.opensource.org/licenses/mit-license.php)
Version: 1.2.1

Optional extensions on the jquery.inputmask base
*/
(function(h){h.extend(h.inputmask.defaults.definitions,{h:{validator:"[01][0-9]|2[0-3]",cardinality:2,prevalidator:[{validator:"[0-2]",cardinality:1}]},s:{validator:"[0-5][0-9]",cardinality:2,prevalidator:[{validator:"[0-5]",cardinality:1}]},d:{validator:"0[1-9]|[12][0-9]|3[01]",cardinality:2,prevalidator:[{validator:"[0-3]",cardinality:1}]},m:{validator:"0[1-9]|1[012]",cardinality:2,prevalidator:[{validator:"[01]",cardinality:1}]},y:{validator:"(19|20)\\d{2}",cardinality:4,prevalidator:[{validator:"[12]",
cardinality:1},{validator:"(19|20)",cardinality:2},{validator:"(19|20)\\d",cardinality:3}]}});h.extend(h.inputmask.defaults.aliases,{"dd/mm/yyyy":{mask:"1/2/y",placeholder:"dd/mm/yyyy",regex:{val1pre:/[0-3]/,val1:/0[1-9]|[12][0-9]|3[01]/,val2pre:function(a){a=h.inputmask.escapeRegex.call(this,a);return RegExp("((0[1-9]|[12][0-9]|3[01])"+a+"[01])")},val2:function(a){a=h.inputmask.escapeRegex.call(this,a);return RegExp("((0[1-9]|[12][0-9])"+a+"(0[1-9]|1[012]))|(30"+a+"(0[13-9]|1[012]))|(31"+a+"(0[13578]|1[02]))")},
yearpre1:/[12]/,yearpre2:/(19|20)/,yearpre3:/(19|20)\d/,year:/(19|20)\d{2}/},leapday:"29/02/",separator:"/",onKeyUp:function(a,e){var c=h(this);if(a.ctrlKey&&a.keyCode==e.keyCode.RIGHT){var d=new Date;c.val(d.getDate().toString()+(d.getMonth()+1).toString()+d.getFullYear().toString())}},definitions:{1:{validator:function(a,e,c,d,b){var f=b.regex.val1.test(a);if(!d&&!f&&(a.charAt(1)==b.separator||-1!="-./".indexOf(a.charAt(1))))if(f=b.regex.val1.test("0"+a.charAt(0)))return e[c-1]="0",{pos:c,c:a.charAt(0)};
return f},cardinality:2,prevalidator:[{validator:function(a,e,c,d,b){var f=b.regex.val1pre.test(a);return!d&&!f&&(f=b.regex.val1.test("0"+a))?(e[c]="0",c++,{pos:c}):f},cardinality:1}]},2:{validator:function(a,e,c,d,b){var f=e.join("").substr(0,3),g=b.regex.val2(b.separator).test(f+a);if(!d&&!g&&(a.charAt(1)==b.separator||-1!="-./".indexOf(a.charAt(1))))if(g=b.regex.val2(b.separator).test(f+"0"+a.charAt(0)))return e[c-1]="0",{pos:c,c:a.charAt(0)};return g},cardinality:2,prevalidator:[{validator:function(a,
e,c,d,b){var f=e.join("").substr(0,3),g=b.regex.val2pre(b.separator).test(f+a);return!d&&!g&&(g=b.regex.val2(b.separator).test(f+"0"+a))?(e[c]="0",c++,{pos:c}):g},cardinality:1}]},y:{validator:function(a,e,c,d,b){if(b.regex.year.test(a)){if(e.join("").substr(0,6)!=b.leapday)return!0;a=parseInt(a,10);return 0===a%4?0===a%100?0===a%400?!0:!1:!0:!1}return!1},cardinality:4,prevalidator:[{validator:function(a,e,c,d,b){var f=b.regex.yearpre1.test(a);return!d&&!f&&(d=(new Date).getFullYear().toString().slice(0,
2),f=b.regex.yearpre3.test(d+a))?(e[c++]=d[0],e[c++]=d[1],{pos:c}):f},cardinality:1},{validator:function(a,e,c,d,b){var f=b.regex.yearpre2.test(a);return!d&&!f&&(d=(new Date).getFullYear().toString().slice(0,2),b.regex.year.test(d+a)?e.join("").substr(0,6)!=b.leapday?f=!0:(b=parseInt(a,10),f=0===b%4?0===b%100?0===b%400?!0:!1:!0:!1):f=!1,f)?(e[c-1]=d[0],e[c++]=d[1],e[c++]=a[0],{pos:c}):f},cardinality:2},{validator:"(19|20)\\d",cardinality:3}]}},insertMode:!1,autoUnmask:!1},"mm/dd/yyyy":{placeholder:"mm/dd/yyyy",
alias:"dd/mm/yyyy",regex:{val2pre:function(a){a=h.inputmask.escapeRegex.call(this,a);return RegExp("((0[13-9]|1[012])"+a+"[0-3])|(02"+a+"[0-2])")},val2:function(a){a=h.inputmask.escapeRegex.call(this,a);return RegExp("((0[1-9]|1[012])"+a+"(0[1-9]|[12][0-9]))|((0[13-9]|1[012])"+a+"30)|((0[13578]|1[02])"+a+"31)")},val1pre:/[01]/,val1:/0[1-9]|1[012]/},leapday:"02/29/",onKeyUp:function(a,e){var c=h(this);if(a.ctrlKey&&a.keyCode==e.keyCode.RIGHT){var d=new Date;c.val((d.getMonth()+1).toString()+d.getDate().toString()+
d.getFullYear().toString())}}},"yyyy/mm/dd":{mask:"y/1/2",placeholder:"yyyy/mm/dd",alias:"mm/dd/yyyy",leapday:"/02/29",onKeyUp:function(a,e){var c=h(this);if(a.ctrlKey&&a.keyCode==e.keyCode.RIGHT){var d=new Date;c.val(d.getFullYear().toString()+(d.getMonth()+1).toString()+d.getDate().toString())}},definitions:{2:{validator:function(a,e,c,d,b){var f=e.join("").substr(5,3),g=b.regex.val2(b.separator).test(f+a);if(!d&&!g&&(a.charAt(1)==b.separator||-1!="-./".indexOf(a.charAt(1))))if(g=b.regex.val2(b.separator).test(f+
"0"+a.charAt(0)))return e[c-1]="0",{pos:c,c:a.charAt(0)};if(g){if(e.join("").substr(4,4)+a!=b.leapday)return!0;a=parseInt(e.join("").substr(0,4),10);return 0===a%4?0===a%100?0===a%400?!0:!1:!0:!1}return g},cardinality:2,prevalidator:[{validator:function(a,e,c,d,b){var f=e.join("").substr(5,3),g=b.regex.val2pre(b.separator).test(f+a);return!d&&!g&&(g=b.regex.val2(b.separator).test(f+"0"+a))?(e[c]="0",c++,{pos:c}):g},cardinality:1}]}}},"dd.mm.yyyy":{mask:"1.2.y",placeholder:"dd.mm.yyyy",leapday:"29.02.",
separator:".",alias:"dd/mm/yyyy"},"dd-mm-yyyy":{mask:"1-2-y",placeholder:"dd-mm-yyyy",leapday:"29-02-",separator:"-",alias:"dd/mm/yyyy"},"mm.dd.yyyy":{mask:"1.2.y",placeholder:"mm.dd.yyyy",leapday:"02.29.",separator:".",alias:"mm/dd/yyyy"},"mm-dd-yyyy":{mask:"1-2-y",placeholder:"mm-dd-yyyy",leapday:"02-29-",separator:"-",alias:"mm/dd/yyyy"},"yyyy.mm.dd":{mask:"y.1.2",placeholder:"yyyy.mm.dd",leapday:".02.29",separator:".",alias:"yyyy/mm/dd"},"yyyy-mm-dd":{mask:"y-1-2",placeholder:"yyyy-mm-dd",leapday:"-02-29",
separator:"-",alias:"yyyy/mm/dd"},"hh:mm:ss":{mask:"h:s:s",autoUnmask:!1},"hh:mm":{mask:"h:s",autoUnmask:!1},date:{alias:"dd/mm/yyyy"},datetime:{mask:"1/2/y h:s",placeholder:"dd/mm/yyyy hh:mm",alias:"date"}})})(jQuery);