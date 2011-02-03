<?php

// delete old files
$base = '../src/net/sf/jsqlparser/parser';
@unlink("$base/TokenMgrError.java");
@unlink("$base/ParseException.java");
@unlink("$base/Token.java");
@unlink("$base/SimpleCharStream.java");

// run parser
system("javacc-5.0/bin/javacc -LOOKAHEAD=2 -OUTPUT_DIRECTORY=`pwd`/$base `pwd`/$base/JSqlParserCC.jj");

?>
