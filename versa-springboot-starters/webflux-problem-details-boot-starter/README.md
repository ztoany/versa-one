# Problem Details for Webflux

## BusinessException Response

fields:

- type, the value is about:blank
- title, the value is Conflict
- status, the value is 409
- detail, the detail message of business exception
- instance, refer to rfc9457
- timestamp, the time when the API was called
- code, business exception code


example:

```json
{
  "type": "about:blank",
  "title": "Conflict",
  "status": 409,
  "detail": "Customer 221878244612243451 not found",
  "instance": "/api/v1/customers/221878244612243451",
  "code": "E10000001",
  "timestamp": "2024-09-04T06:35:47.923+00:00"
}
```

## Field Restrictions Response

fields:

- type, the value is about:blank
- title, the value is Bad Request
- status, the value is 400
- detail, the value is Invalid request content.
- instance, refer to rfc9457
- timestamp, the time when the API was called
- violations, the information that violates the field restrictions

example:

```json
{
  "type": "about:blank",
  "title": "Bad Request",
  "status": 400,
  "detail": "Invalid request content.",
  "instance": "/api/v1/customers",
  "violations": [
    {
      "field": "name",
      "rejectedValue": "",
      "message": "name is required"
    }
  ],
  "timestamp": "2024-09-04T06:43:31.604+00:00"
}
```