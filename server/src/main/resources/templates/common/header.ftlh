<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Powwow</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.8.0/css/bulma.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400&display=swap" rel="stylesheet">
    <script defer src="https://use.fontawesome.com/releases/v5.3.1/js/all.js"></script>
</head>
<body>
<nav class="navbar is-info">
    <div class="container">
        <div class="navbar-menu">
            <div class="navbar-start">
                <a class="navbar-item" href="/">
                    <b>Powwow</b>
                </a>
                <#assign page = page!'Executions'>

                <#if page == 'Executions'>
                    <a href="/" class="navbar-item is-active">
                        Executions
                    </a>
                <#else>
                    <a href="/" class="navbar-item">
                        Executions
                    </a>
                </#if>

                <#if page == 'Definitions'>
                    <a href="/fsm" class="navbar-item is-active">
                        Definitions
                    </a>
                <#else>
                    <a href="/fsm" class="navbar-item">
                        Definitions
                    </a>
                </#if>

                <#if page == 'Health'>
                    <a href="/health" class="navbar-item is-active">
                        Health
                    </a>
                <#else>
                    <a href="/health" class="navbar-item">
                        Health
                    </a>
                </#if>

                <#if page == 'Metrics'>
                    <a href="#" class="navbar-item is-active">
                        Metrics
                    </a>
                <#else>
                    <a href="#" class="navbar-item">
                        Metrics
                    </a>
                </#if>
            </div>

            <div class="navbar-end">
                <div class="navbar-item has-dropdown is-hoverable">
                    <a class="navbar-link">
                        <i class="fa fa-question-circle"></i>
                    </a>

                    <div class="navbar-dropdown is-right">
                        <a class="navbar-item" href="#">
                            <div class="field">
                                        <span class="icon is-medium">
                                           <i class="fa fa-book"></i>
                                        </span>
                                <span>Documentation</span>
                            </div>
                        </a>
                        <hr class="navbar-divider">

                        <a class="navbar-item" href="#">
                            <div class="field">
                                        <span class="icon is-medium">
                                          <i class="fas fa-info" aria-hidden="true"></i>
                                        </span>
                                <span>About</span>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</nav>
