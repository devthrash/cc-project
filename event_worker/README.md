# Feed Worker

Listens to posts events and stores data in MongoDB.

### Message format

#### Fields
- event: String, enum ``["post-created", "post-updated", "post-deleted"]``
- data: Object

#### Example
```json
{
  "event": "post-created",
  "data": {
    "uuid": "00000000-0000-0000-0000-000000000000",
    "title": "Post title",
    "content": "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
  },
}
```
