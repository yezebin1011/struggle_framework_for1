
  Ext.onReady(function(){
	  
	  var consumer_name = document.getElementById("loginUserName").value;
	  
	  var treeStore = Ext.create('Ext.data.TreeStore',{
		    proxy : {
		    	type: 'ajax',
		    	url : 'module!getChildModuleInfo.action?module_id=0&consumer_name='+consumer_name
		    },
		    root: {
                text: '管理系统',
                id: 0,
                expanded: true,
                expandable: true,
                leaf: false
            }
	  });
	  
	  var leftFrame = window.top.document.getElementById("leftFrame");
	  
	  var tree = Ext.create('Ext.tree.Panel', {
          store: treeStore,
          layout : 'fit',
          title: '',
          id: "module_tree",
          border: false,
          renderTo: "mainTree",
          width: 190,
          height: leftFrame.height - document.getElementById("left_menu").offsetHeight,
          autoScroll: true,
          animate: true,
          containerScroll: true
      });
	  
	  tree.on('beforeload',function(treeStore,node){
		  treeStore.proxy.url = 'module!getChildModuleInfo.action?module_id='+node.id+"&&consumer_name="+consumer_name;
	  });
	  
	  tree.getRootNode().expand();
  });

  