FROM maven:3.8.1-openjdk-16

# The following steps are from https://github.com/Zenika/alpine-chrome/blob/master/with-chromedriver/Dockerfile
# Install latest Chromium package.
RUN echo "http://dl-cdn.alpinelinux.org/alpine/edge/main" > /etc/apk/repositories \
    && echo "http://dl-cdn.alpinelinux.org/alpine/edge/community" >> /etc/apk/repositories \
    && echo "http://dl-cdn.alpinelinux.org/alpine/edge/testing" >> /etc/apk/repositories \
    && echo "http://dl-cdn.alpinelinux.org/alpine/v3.12/main" >> /etc/apk/repositories \
    && apk upgrade -U -a \
    && apk add \
    libstdc++ \
    chromium \
    harfbuzz \
    nss \
    freetype \
    ttf-freefont \
    font-noto-emoji \
    wqy-zenhei \
    && rm -rf /var/cache/* \
    && mkdir /var/cache/apk

COPY local.conf /etc/fonts/local.conf

# Add Chrome as a user
RUN mkdir -p /usr/src/app \
    && adduser -D chrome \
    && chown -R chrome:chrome /usr/src/app
# Run Chrome as non-privileged
USER chrome
WORKDIR /usr/src/app

RUN wget https://raw.githubusercontent.com/jfrazelle/dotfiles/master/etc/docker/seccomp/chrome.json

ENV CHROME_BIN=/usr/bin/chromium-browser \
    CHROME_PATH=/usr/lib/chromium/

# Install Chromedriver latest
USER root
RUN apk add --no-cache chromium-chromedriver
USER chrome

ENTRYPOINT ["chromium-browser", "--headless", "--use-gl=swiftshader", "--disable-software-rasterizer", "--disable-dev-shm-usage", "--rm", "--security-opt seccomp=chrome.json"]
