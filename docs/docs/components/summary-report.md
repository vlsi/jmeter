---
title: Summary Report
sidebar_position: 16
---

# Summary Report

![Summary Report](/img/images/screenshots/summary_report.png)

[Aggregate Report](#aggregate-report), except that it uses less memory.The throughput is calculated from the point of view of the sampler target
(e.g. the remote server in the case of HTTP samples).
JMeter takes into account the total time over which the requests have been generated.
If other samplers and timers are in the same thread, these will increase the total time,
and therefore reduce the throughput value.
So two identical samplers with different names will have half the throughput of two samplers with the same name.
It is important to choose the sampler labels correctly to get the best results from
the Report.

- `Label` - The label of the sample.
If "`Include group name in label?`" is selected, then the name of the thread group is added as a prefix.
This allows identical labels from different thread groups to be collated separately if required.
- `# Samples` - The number of samples with the same label
- `Average` - The average elapsed time of a set of results
- `Min` - The lowest elapsed time for the samples with the same label
- `Max` - The longest elapsed time for the samples with the same label
- `Std. Dev.` - the [Standard Deviation](glossary.html#StandardDeviation) of the sample elapsed time
- `Error %` - Percent of requests with errors
- `Throughput` - the [Throughput](glossary.html#Throughput) is measured in requests per second/minute/hour.
The time unit is chosen so that the displayed rate is at least `1.0`.
When the throughput is saved to a CSV file, it is expressed in requests/second,
i.e. 30.0 requests/minute is saved as `0.5`.
- `Received KB/sec` - The throughput measured in Kilobytes per second
- `Sent KB/sec` - The throughput measured in Kilobytes per second
- `Avg. Bytes` - average size of the sample response in bytes.

Times are in milliseconds.

