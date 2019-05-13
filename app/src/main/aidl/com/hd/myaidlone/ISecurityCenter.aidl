// ISecurityCenter.aidl
package com.hd.myaidlone;

// Declare any non-default types here with import statements

interface ISecurityCenter {
  String encypt(String content);
  String decrypt(String password);
}
