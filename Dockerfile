FROM ubuntu:latest
LABEL authors="gaspa"

ENTRYPOINT ["top", "-b"]