package com.github.unldenis.premiumsponge.task;

public interface Workload {

  void compute();

  default boolean reschedule() {
    return false;
  }

}