# PrismaticLibe

# About

Prismatic is a library for colored items, armors, and GUI affects for BTA. It allows for item textures to be colored and layered together like spawn eggs are in modern mc, it also allows for the same with armor textures allowing for features like dyeable armors or armor trims in BTA.

# Installation
This mod needs a specific [MultiMC/Prism](https://github.com/Turnip-Labs/babric-instance-repo) instance to run and another other mod as a dependency:
- [HalpLibe](https://github.com/Turnip-Labs/bta-halplibe/releases) by Turnip Labs

## How to include PrismaticLibe in a project
Add this in your `build.gradle`:
```groovy
repositories {
    ivy {
		url = "https://github.com/UselessSolutions"
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
prismatic_version=3.0.3-7.1
```
