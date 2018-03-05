# Job Selection

## Classes & Interfaces

### `Importer`

```java
File jobsFile = new File(path + "/jobs.csv");
// ...
Importer importer = new Importer(jobsFile, cancellationsFile, locationsFile, itemsFile, dropsFile);
importer.parse();

List<Job> jobs = importer.getJobs();
Set<Location> drops = importer.getDrops();
List<Item> items = importer.getItems();
```

### `IJobSelector`

Used for selelecting the next highest priority item from a list.

All job selectors implement `IJobSelector`.
* `PriorityJobSelector` uses a queue to select the next highest priority job

```java
IJobSelector selector = new PriorityJobSelector(jobs);
// ...
Job nextJob = selector.next();
```

### `Location`
Stores an (x, y) coordinate of the warehouse.

### `Item`
Stores an item id, reward, weight and references a pickup location.

### `JobItem`
References an item and stores how many of the item are required to complete the job

### `Job`
Stores a job id, a list of job items and whether the job has been previously cancelled.

## Input CSV Files

### `jobs.csv`

* Job ID
* 1st Item ID
* 1st Item Count (> 0)
* 2nd Item ID
* 2nd Item Count (> 0)
* ...
* nth Item ID
* nth Item Count (> 0)

Format: `<job id - int>, <item 1 id - alpha>, <item 1 count - int>, <item 2 id - alpha>, <item 2 count - int>, ...`

Regex: `^([0-9]+)(\s*,\s*([a-z]+)\s*,\s*([1-9][0-9]*))+$`

Regex (doesn't match with duplicate items): `^([0-9]+)(\s*,\s*([a-z]+)(?!.+\3)\s*,\s*([1-9][0-9]*))+$` (e.g. `1000,a,3,b,2,a,6` is ignored as item `a` appears twice)

```csv
10000,fc,2,ge,3,dc,1,ej,1
10001,de,1,ca,1
10002,ej,4,de,2,fa,1,cj,4,ca,2,dc,1,bh,1,eg,1,fc,2
10003,bh,1
10004,dc,1,fa,2,ej,4,fc,2,bh,3
```

### `cancellations.csv`

* Job ID
* Cancelled?

Format: `<job id - int>, <cancelled - boolean>`

Regex: `^([0-9]+)\s*,\s*([0-1])$`

```csv
10100,0
10101,0
10102,0
10103,1
10104,1
```

### `items.csv`

* Item ID
* Reward: for picking and dropping off one of the item
* Weight: for carrying one of the item

Format: `<item id - alpha>, <reward - float>, <weight - float>`

Regex: `^([a-z]+)\s*,\s*([0-9]+(?:\.[0-9]+)?)\s*,\s*([0-9]+(?:\.[0-9]+)?)$`

```csv
aa,11.10,1.23
ab,12.01,3.93
ac,12.36,4.28
ad,22.93,5.61
be,9.62,2.61
```

### `locations.csv`

* x coordinate
* y coordinate
* Item ID

Format: `<x coordinate - int>, <y coordinate - int>, <item id - alpha>`

Regex: `^([0-9]+)\s*,\s*([0-9]+)\s*,\s*([a-z]+)$`

```csv
2,1,aa
2,2,ab
2,3,ac
2,4,ad
2,5,be
```

### `drops.csv`

* x coordinate
* y coordinate

Format: `<x coordinate - int>, <y coordinate - int>`

Regex: `^([0-9]+)\s*,\s*([0-9]+)$`

```csv
4,7
7,7
```
