#!/bin/bash
set -e
# Install launch scripts and galleon common content.
SCRIPT_DIR=$(pwd -P)/$(dirname $0)
tmp_dir="$SCRIPT_DIR/target/tmp"
mkdir -p $tmp_dir
resources_dir="$SCRIPT_DIR/target/resources"
pushd "$tmp_dir" > /dev/null
  echo "git clone -b $WILDFLY_CEKIT_TAG https://github.com/$WILDFLY_CEKIT_FORK/wildfly-cekit-modules"
  git clone -b $WILDFLY_CEKIT_TAG https://github.com/$WILDFLY_CEKIT_FORK/wildfly-cekit-modules
  pushd wildfly-cekit-modules/export
    bash ./export-cekit-modules.sh "$resources_dir" "$SCRIPT_DIR/wf-cekit-modules-fp-content-list.txt" "$SCRIPT_DIR/wf-cekit-modules-launch-list.txt" "$SCRIPT_DIR/wf-cekit-modules-launch-config-list.txt"
  popd > /dev/null
popd > /dev/null