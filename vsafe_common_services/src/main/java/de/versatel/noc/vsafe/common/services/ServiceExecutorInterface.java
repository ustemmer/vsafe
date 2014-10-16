package de.versatel.noc.vsafe.common.services;

import java.util.List;

/**
 *
 * @author ulrich.stemmer
 */
public interface ServiceExecutorInterface {
      public Object set(int methodId, List<Object> arguments);
      public Object get(int methodId, List<Object> arguments);
}
