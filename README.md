# PrismaticLibe

Useless' mod library.


## How to include PrismaticLibe in a project
Add this in your `build.gradle`:
```groovy
repositories {
    ivy {
        url = "https://github.com/UselessBullets"
        patternLayout {
            artifact "[organisation]/releases/download/v[revision]/[module]-[revision].jar"
            m2compatible = true
        }
        metadataSources { artifact() }
    }
}

dependencies {

    modImplementation "BTA_Babric_PrismaticLibe:prismaticlibe:${project.prismatic_version}"
   
}
```
And add this to your `gradle.properties`
```groovy
prismatic_version=2.0.2
```
