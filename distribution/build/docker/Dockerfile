FROM centos:7.5.1804

# set label
LABEL maintainer="ryan zhang<rainbow954@163.com>"

# set environment
ENV SEATA_USER="seata" \
    JAVA_HOME="/usr/lib/jvm/java-1.8.0-openjdk" \
    JAVA="/usr/lib/jvm/java-1.8.0-openjdk/bin/java" \
    TIME_ZONE="Asia/Shanghai" 

ARG SEATA_VERSION=0.7.1

WORKDIR /$BASE_DIR

RUN set -x \
    && yum update -y \
    && yum install -y java-1.8.0-openjdk java-1.8.0-openjdk-devel wget iputils nc vim libcurl \
    && mkdir /opt/seata \
    && ln -snf /usr/share/zoneinfo/$TIME_ZONE /etc/localtime && echo '$TIME_ZONE' > /etc/timezone \
    && yum clean all
COPY maven/bin/. /opt/seata/bin
COPY maven/conf/. /opt/seata/conf
COPY maven/lib/. /opt/seata/lib
# 设置额外参数
ENV EXTRA_JVM_ARGUMENTS="-XX:MaxDirectMemorySize=1024M"

ENTRYPOINT ["sh","/opt/seata/bin/seata-server.sh"]
EXPOSE 8091

