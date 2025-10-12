package springai

import groovy.transform.ToString

@ToString(includePackage = false)
record Activity(String activity,
                String location,
                String day,
                String time) {
}
