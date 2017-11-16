Raspark
=========

This ansible installs a cluster of Raspberry Pi, including master and workers of yarn os spark standalone (as you wish),
 a node with a PostgreSQL and other with Apache Zeppelin. All of this is configurable, you should not install Zeppelin or PostgreSQL and 
 you can configure the number of executors.

Motivations
------------

We are Datio, so we are a little nerds... Furthermore we wanted to demostrate that with a bit of money we can build a spark cluster.

Requirements
------------

Some rasberry pi (sorry, too much obviously). Our test is with two raspberry pi 2 and five rasberry pi 3, a switch for connect them to the network, and Internet for 
download the packets from repositories.

Inventory example: 
------------

Here a inventory example to can explain how configure the cluster:
```
  {{hostname-1}}   ansible_host={{ip1}} ansible_user={{os_user_rasp_1}} ansible_ssh_pass={{os_pass_rasp_1}} ansible_ssh_common_args='-o StrictHostKeyChecking=no'
  {{hostname-2}}   ansible_host={{ip2}} ansible_user={{os_user_rasp_2}} ansible_ssh_pass={{os_pass_rasp_2}} ansible_ssh_common_args='-o StrictHostKeyChecking=no'
  ...
  ...

  [masters:vars]
  hadoop_is_namenode={{true|false}}
  hadoop_is_resourcemanager={{true|false}}
  spark_is_master={{true|false}}

  [masters]
  hostname-1
  ...
  ...

  [workers:vars]
  hadoop_is_datanode={{true|false}}
  hadoop_is_nodemanager={{true|false}}
  spark_is_worker={{true|false}}

  [workers]
  hostname-2
  ...
  ...

  [postgresql]
  ...
  ...

  [zeppelin]
  ...
  ...

```

Explain use:
------------

You can find more information into each roles, but we are going to do a little abstract for you:
  * If you want a yarn cluster, you must configure in vars master:
     - hadoop_is_namenode: true
     - hadoop_is_resourcemanager=true
     - spark_is_master=false
  and worker vars:
     - hadoop_is_datanode=true
     - hadoop_is_nodemanager=true
     - spark_is_worker=false
  * If you want a spark standalone cluster, you must configure in vars master:
     - hadoop_is_namenode: true
     - hadoop_is_resourcemanager=false
     - spark_is_master=true
  and worker vars:
     - hadoop_is_datanode=true
     - hadoop_is_nodemanager=false
     - spark_is_worker=true  

Licenses
-------

Apache, PostgreSQL
