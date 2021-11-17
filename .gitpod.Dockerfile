FROM gitpod/workspace-full-vnc

USER root

RUN add-apt-repository universe
RUN apt update
RUN apt -y install graphviz

USER gitpod

RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh \
             && sdk install java 16.0.1.hs-adpt \
             && sdk default java 16.0.1.hs-adpt"
             
RUN apt-get update \
    && apt-get install -y openjfx libopenjfx-java matchbox \
    && apt-get clean && rm -rf /var/cache/apt/* && rm -rf /var/lib/apt/lists/* && rm -rf /tmp/*
