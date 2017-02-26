#!/usr/bin/env bash

#Download the common crawl dataset into specific gs bucket

GS_BUCKET=gs://bkt-common-crawl-201701/download/


gcloud auth login

#wget -P $GS_BUCKET https://commoncrawl.s3.amazonaws.com/crawl-data/CC-MAIN-2017-04/robotstxt.paths.gz
#wget -P $GS_BUCKET https://commoncrawl.s3.amazonaws.com/crawl-data/CC-MAIN-2017-04/warc.paths.gz

gsutil cp s3://commoncrawl/crawl-data/CC-MAIN-2017-04/warc.paths.gz ${GS_BUCKET}
gsutil cp s3://commoncrawl/crawl-data/crawl-data/CC-MAIN-2017-04/robotstxt.paths.gz ${GS_BUCKET}

gsutil cp s3://commoncrawl/crawl-data/CC-MAIN-2017-04/warc.paths.gz ${GS_BUCKET}
gsutil cp https://commoncrawl.s3.amazonaws.com/crawl-data/CC-MAIN-2017-04/robotstxt.paths.gz ${GS_BUCKET}

wget -P $GS_BUCKET