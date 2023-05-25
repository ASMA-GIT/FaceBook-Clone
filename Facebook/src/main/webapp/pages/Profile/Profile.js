/* perform any action on widgets within this block */
Page.onReady = function() {
    /*
     * widgets can be accessed through 'Page.Widgets' property here
     * e.g. to get value of text widget named 'username' use following script
     * 'Page.Widgets.username.datavalue'
     */
};


Page.editUsersForm1_1Beforesubmit = function($event, widget, $data) {
    if (Page.Variables.FileServiceUploadFile5.dataSet.length > 0) {

        $data.Users.coverImage = Page.Variables.FileServiceUploadFile5.dataSet[0].path;
    }

};

Page.editUsersForm1Beforesubmit = function($event, widget, $data) {
    debugger;
    if (Page.Variables.FileServiceUploadFile3.dataSet.length > 0) {

        $data.Users.profileImage = Page.Variables.FileServiceUploadFile3.dataSet[0].path;
    }
};