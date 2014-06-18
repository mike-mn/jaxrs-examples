jaxrs-examples
==============

Minimal examples to illustrate and test the RAML codegen plugin and its generated code

## Requirements

- The RAML examples only work with the "no-wrapper" version of the [raml-jaxrs-plugin](raml-jaxrs-plugin/README.md)

## Build

    mvn install


## Run Server

    mvn --projects example-cxf-server exec:java

## Run Client

    mvn --projects example-cxf-client exec:java

