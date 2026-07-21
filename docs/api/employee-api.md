# Employee REST API

Base path: `/api/employees`

| Method | Path | Description |
| --- | --- | --- |
| `GET` | `/api/employees` | List employees sorted by ID. |
| `GET` | `/api/employees/{id}` | Get one employee by ID. |
| `POST` | `/api/employees` | Create an employee. |
| `PUT` | `/api/employees/{id}` | Replace an employee. |
| `DELETE` | `/api/employees/{id}` | Delete an employee. |

Request body for create and update:

```json
{
  "firstName": "Ada",
  "lastName": "Lovelace",
  "email": "ada@example.com",
  "jobTitle": "Engineer",
  "department": "Technology",
  "salary": 125000,
  "hireDate": "2024-01-15"
}
```

Validation errors return `400`, missing employees return `404`, and duplicate emails return `409`.
