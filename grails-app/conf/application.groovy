grails{
    profile = 'web-plugin'
    codegen{
        defaultPackage = 'grails.excel.import'
    }
}

info{
    app{
        name = '@info.app.name@'
        version = '@info.app.version@'
        grailsVersion = '@info.app.grailsVersion@'
    }
}

spring {
    groovy{
        template {
            "check-template-location" = false
        }
    }
}