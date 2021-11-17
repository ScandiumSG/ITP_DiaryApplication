FROM gitpod/workspace-full-vnc

USER root

RUN add-apt-repository universe
RUN apt update
RUN apt -y install graphviz

USER gitpod

RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh \
             && sdk install java 16.0.1.hs-adpt \
             && sdk default java 16.0.1.hs-adpt"
