<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>用户信息管理</title>
    <script src="/js/jquery-1.12.3.min.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/jquery.bootgrid.min.css">
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="/js/jquery.bootgrid.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap-datetimepicker.min.js"></script>
    <link rel="stylesheet" href="/css/bootstrap-datetimepicker.min.css" type="text/css"></link>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <nav class="navbar navbar-inverse">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                                data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar">*****</span>
                        </button>
                        <a class="navbar-brand" href="#">用户信息管理</a>
                    </div>
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav">
                            <li><a href="/logout">logout</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <h2>用户信息</h2>
            <a class="btn btn-primary" href="/logined/exportUser">导出数据为excel</a>
            <a class="btn btn-primary" href="#" id="add">添加用户</a>

            <table id="grid-data" class="table table-condensed table-hover table-striped">
                <thead>
                <tr>
                    <th data-column-id="userId" data-identifier="true" data-type="numeric">userId</th>
                    <th data-column-id="userName" data-identifier="true" >userName</th>
                    <th data-column-id="permissions">permissions</th>
                    <th data-column-id="password" data-order="desc">password</th>
                    <th data-column-id="nickName">nickName</th>
                    <th data-column-id="commands" data-formatter="commands" data-sortable="false">Commands</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        var grid;
        grid = $("#grid-data").bootgrid({
            ajax: true,
            post: function () {
                return {
                    id: "b0df282a-0d67-40e5-8558-c9e93b7befed"
                };
            },
            url: "/logined/userList",
            formatters: {
                "commands": function (column, row) {
                    return "<button type=\"button\" class=\"btn btn-xs btn-default command-edit\" data-row-id=\"" + row.userId + "\">编辑<span class=\"fa fa-pencil\"></span></button> " +
                        "<button type=\"button\" class=\"btn btn-xs btn-default command-delete\" data-row-id=\"" + row.userId + "\">删除<span class=\"fa fa-trash-o\"></span></button>";
                }
            }
        }).on("loaded.rs.jquery.bootgrid", function () {
            grid.find(".command-edit").on("click", function (e) {
                $(".stumodal").modal();
                $.post("/logined/getUserInfo", {userId: $(this).data("row-id")}, function (str) {
                    $("#userId2").val(str.userId);
                    $("#userName2").val(str.userName);
                    $("#permissions2").val(str.permissions);
                    $("#password2").val(str.password);
                    $("#nickName2").val(str.nickName);
                });
            }).end().find(".command-delete").on("click", function () {
                alert("You pressed delete on row: " + $(this).data("row-id"));
                $.post("/logined/delUser", {userId: $(this).data("row-id")}, function () {
                    alert("删除成功");
                    $("#grid-data").bootgrid("reload");
                });
            });
        });
    });

    $(document).ready(function () {
        $("#add").click(function () {
            $(".addmodal").modal();
        });
    });

</script>

<div class="modal fade stumodal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">update Stu</h4>
            </div>
            <form action="${pageContext.request.contextPath}/logined/updateUser" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="userId2">userId</label>
                        <input type="text" name="userId" class="form-control" id="userId2" readonly="true">
                    </div>
                    <div class="form-group">
                        <label for="userName2">userName</label>
                        <input type="text" name="userName" class="form-control" id="userName2" readonly="true">
                    </div>
                    <div class="form-group">
                        <label for="permissions2">permissions</label>
                        <input type="text" name="permissions" class="form-control" id="permissions2">
                    </div>
                    <div class="form-group">
                        <label for="password2">password</label>
                        <input type="text" name="password" class="form-control" id="password2">
                    </div>
                    <div class="form-group">
                        <label for="nickName2">nickName</label>
                        <input type="text" name="nickName" class="form-control" id="nickName2">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Save changes</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade addmodal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">add stu</h4>
            </div>
            <form action="/logined/addUser" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="userName1">userName</label>
                        <input type="text" name="userName" class="form-control" id="userName1">
                    </div>
                    <div class="form-group">
                        <label for="permission1">permissions</label>
                        <input type="text" name="permissions" class="form-control" id="permission1">
                    </div>
                    <div class="form-group">
                        <label for="password1">password</label>
                        <input type="text" name="password" class="form-control" id="password1">
                    </div>
                    <div class="form-group">
                        <label for="nickName1">nickName</label>
                        <input type="text" name="nickName" class="form-control" id="nickName1">
                    </div>
                    <div class="form-group">
                        <input type="hidden" name="Id" class="form-control" id="Id">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Add Stu</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>