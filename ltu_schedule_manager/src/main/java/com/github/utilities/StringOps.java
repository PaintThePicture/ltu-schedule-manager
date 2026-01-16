/*
 * ltu-schedule-manager: Ett verktyg för att hantera och exportera scheman 
 * från TimeEdit till Canvas för studenter vid Luleå tekniska universitet.
 *
 * Copyright (C) 2025  Alexander Edemalm, 
 * Ronak Olyaee, Therese Henriksson, Jakob Nilsson
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA 02110-1301 USA.
 */
package com.github.utilities;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringOps {
    
    public static interface InnerStringOps {
        
        public static <T> Stream<String> stemof(T input) {
            if(input instanceof String) {
                return Stream.of((String) input);
            } else if (input instanceof String[]) {
                return Arrays.stream((String[]) input);
            } else if (input instanceof Stream) {
                return (Stream<String>) input;
            }
            throw new UnsupportedOperationException(
                        "Error: Unsuported input type " + input != null ? input.getClass().getName() : "null" );
        }
    }

    public static class UtilFormat implements InnerStringOps {
        
        private static interface Operations {
            
            static Stream<String> upperRestLowerCaseAsStream(Stream<String> value) {
                return value.map(String::trim)
                            .map(s -> s.substring(0, 1).toUpperCase() +
                                      s.substring(1).toLowerCase());
            }

            static Stream<String> uppercasingAsStream(Stream<String> value) {
                return value.map(String::trim)
                            .map(String::toUpperCase);
            }

            static Stream<String> lowercasingAsStream(Stream<String> value) {
                return value.map(String::trim) 
                            .map(String::toLowerCase);
            }
        }

        public static <T> Stream<String> upperRestLowerCaseAsStream(T value) {
            return Operations.upperRestLowerCaseAsStream(InnerStringOps.stemof(value));
        }

        public static <T> Stream<String> lowercasingAsStream(T value) {
            return Operations.lowercasingAsStream(InnerStringOps.stemof(value));
        }
        
        public static <T> Stream<String> uppercasingAsStream(T value) {
            return Operations.uppercasingAsStream(InnerStringOps.stemof(value));
        }

        public static <T> String[] upperRestLowerCase(T value) {
            return Operations.upperRestLowerCaseAsStream(InnerStringOps.stemof(value)).toArray(String[]::new);
        }

        public static <T> String[] lowercasing(T value) {
            return Operations.lowercasingAsStream(InnerStringOps.stemof(value)).toArray(String[]::new);
        }
        
        public static <T> String [] uppercasing(T value) {
            return Operations.uppercasingAsStream(InnerStringOps.stemof(value)).toArray(String[]::new);
        }

        public static <T> String  upperRestLowerCaseString(T value) {
            return Operations.upperRestLowerCaseAsStream(InnerStringOps.stemof(value))
                             .collect(Collectors.joining("\\s+"));
        }

        public static <T> String lowercasingString(T value) {
            return Operations.lowercasingAsStream(InnerStringOps.stemof(value))
                             .collect(Collectors.joining("\\s+"));
        }
        
        public static <T> String uppercasingString(T value) {
            return Operations.uppercasingAsStream(InnerStringOps.stemof(value))
                             .collect(Collectors.joining("\\s+"));
        }
    }

    public static class UtilAppend implements InnerStringOps {
        
    }

    public static class UtilSplit implements InnerStringOps {

        private static interface Operations {

            static Stream <String> spaceAsStream(Stream<String> value) {
                    return value.map(String::trim)
                                .flatMap(s -> Arrays.stream(s.split("\\s+")));
            }

            static Stream <String> commaAsStream(Stream<String> value) {
                return value.map(String::trim)
                            .flatMap(s -> Arrays.stream(s.split(",")));
            }

            static Stream<String> whereAsStream(Stream<String> value, String delimeter) {
                return value.map(String::trim)
                            .flatMap(s -> Arrays.stream(s.split(delimeter)));
            }

            static Stream<String> filterAsStream(Stream<String> value, String target, String replace) {
                return value.map(String::trim)
                            .map(s -> s.replace(target, replace))
                            .flatMap(s -> Arrays.stream(s.split(replace)));
            }

            static Stream<String> extractIndexedFirstAsStream(Stream<String> value) {
                return value.map(String::trim)
                                .map(s -> {
                                    String[] parts = s.split("\\s+");
                                    if (parts.length > 0) {
                                        return parts[0];
                                    } else {
                                        return "";
                                    }
                                })
                                .filter(s -> !s.isEmpty());
            }

            static Stream<String> extractIndexedLastAsStream(Stream<String> value) {
                return value.map(String::trim)
                                .map(s -> {
                                    String[] parts = s.split("\\s+");
                                    if (parts.length > 0) {
                                        return parts[1];
                                    } else {
                                        return "";
                                    }
                                })
                                .filter(s -> !s.isEmpty());
            }

            static Stream<String> extractIndexedPartsAsStream(Stream<String> value, int index) {
                return value.map(String::trim)
                            .map(s -> {
                                String[] parts = s.split("\\s+");
                                if (index >= 0 && index < parts.length) {
                                    return String.valueOf(parts[index]);
                                } else {
                                    return null;
                                }
                            })
                            .filter(java.util.Objects::nonNull);
            }
        }

        public static <T> Stream<String> spaceAsStream(T value) {
            return Operations.spaceAsStream(InnerStringOps.stemof(value));
        }

        public static <T> Stream<String> commaAsStream(T value) {
            return Operations.commaAsStream(InnerStringOps.stemof(value));
        }

        public static <T> Stream<String> whereAsStream(T value, String delimeter) {
            return Operations.whereAsStream(InnerStringOps.stemof(value), delimeter);
        }

        public static <T> Stream<String> filterAsStream(T value, String target, String replace) {
            return Operations.filterAsStream(InnerStringOps.stemof(value), target, replace);
        }

        public static <T> Stream<String> extractIndexedFirstAsStream(T value) {
            return Operations.extractIndexedFirstAsStream(InnerStringOps.stemof(value));
        }
        
        public static <T> Stream<String> extractIndexedLastAsStream(T value) {
            return Operations.extractIndexedLastAsStream(InnerStringOps.stemof(value));
        }
        
        public static <T> Stream<String> extractIndexedPartsAsStream(T value, int indexed) {
            return Operations.extractIndexedPartsAsStream(InnerStringOps.stemof(value), indexed);
        }

        public static <T> String[] space(T value) {
            return Operations.spaceAsStream(InnerStringOps.stemof(value)).toArray(String[]::new);
        }

        public static <T> String[] comma(T value) {
            return Operations.commaAsStream(InnerStringOps.stemof(value)).toArray(String[]::new);
        }

        public static <T> String[] where(T value, String delimeter) {
            return Operations.whereAsStream(InnerStringOps.stemof(value), delimeter).toArray(String[]::new);
        }

        public static <T> String[] filter(T value, String target, String replace) {
            return Operations.filterAsStream(InnerStringOps.stemof(value), target, replace).toArray(String[]::new);
        }

        public static <T> String [] extractIndexedFirst(T value) {
            return Operations.extractIndexedFirstAsStream(InnerStringOps.stemof(value)).toArray(String[]::new);
        }
        
        public static <T> String [] extractIndexedLast(T value) {
            return Operations.extractIndexedLastAsStream(InnerStringOps.stemof(value)).toArray(String[]::new);
        }
        
        public static <T> String [] extractIndexedParts(T value, int indexed) {
            return Operations.extractIndexedPartsAsStream(InnerStringOps.stemof(value), indexed).toArray(String[]::new);
        }        
    }
}
