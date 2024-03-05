import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Alert, Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { useCookies } from 'react-cookie';
import formatErrorMessage from './ErrorUtils';

const CustomerEdit = () => {
  const initialFormState = {
    name: 'WonderfullSE',
    email: 'WonderfullSE@techlover.wiki',
    date: new Date(),
  };

  const [customer, setCustomer] = useState(initialFormState);
  const navigate = useNavigate();
  const { id } = useParams();
  const [error, setError] = useState(null);

  useEffect(() => {
    if (id !== 'new') {
      fetch(`/api/customers/${id}`)
        .then(response => response.json())
        .then(data => setCustomer(data))
        .catch(error => {
          console.error('Error fetching data:', error);
          setError(error.message)
        });;
    }
  }, [id, setCustomer]);

  const handleChange = (event) => {
    const { name, value } = event.target

    setCustomer({ ...customer, [name]: value })
  }

  const handleSubmit = async (event) => {
    event.preventDefault();

    const response = await fetch(`/api/customers${customer.id ? `/${customer.id}` : ''}`, {
      method: (customer.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(customer),
    }).then(response => {
      if (!response.ok) {
        throw response.clone();
      }
      setCustomer(initialFormState);
      navigate('/customers');
    }).catch((error) => {
      formatErrorMessage(error, setError);
    })
  }

  const title = <h2>{customer.id ? 'Edit Customer' : 'Add Customer'}</h2>;

  return (<div>
    <AppNavbar />
    <Container>
      {title}

      {error && <Alert color="danger">
        <p><b>Error:</b></p>
        <ul>
          {error.split('\n').map((message, index) => (
            <li key={index}>{message}</li>
          ))}
        </ul>
      </Alert>}
      <Form onSubmit={handleSubmit}>
        <FormGroup>
          <Label for="name">Owner id</Label>
          <Input type="text" name="name" id="name" value={customer.name || ''}
            onChange={handleChange} autoComplete="name" />
        </FormGroup>
        <FormGroup>
          <Label for="email">Currency</Label>
          <Input type="email" name="email" id="email" value={customer.email || ''}
            onChange={handleChange} autoComplete="email" />
        </FormGroup>
        <FormGroup>
          <Label for="dateOfBirth">Date Of Birth</Label>
          <Input type="date" name="dateOfBirth" id="dateOfBirth" value={customer.dateOfBirth || ''}
            onChange={handleChange} autoComplete="balance" />
        </FormGroup>
        {/* <div className="row">
            <FormGroup className="col-md-4 mb-3">
              <Label for="stateOrProvince">State/Province</Label>
              <Input type="text" name="stateOrProvince" id="stateOrProvince" value={customer.stateOrProvince || ''}
                     onChange={handleChange} autoComplete="address-level1"/>
            </FormGroup>
            <FormGroup className="col-md-5 mb-3">
              <Label for="country">Country</Label>
              <Input type="text" name="country" id="country" value={customer.country || ''}
                     onChange={handleChange} autoComplete="address-level1"/>
            </FormGroup>
            <FormGroup className="col-md-3 mb-3">
              <Label for="country">Postal Code</Label>
              <Input type="text" name="postalCode" id="postalCode" value={customer.postalCode || ''}
                     onChange={handleChange} autoComplete="address-level1"/>
            </FormGroup>
          </div> */}
        <FormGroup>
          <Button color="primary" type="submit">Save</Button>{' '}
          <Button color="secondary" tag={Link} to="/customers">Cancel</Button>
        </FormGroup>
      </Form>
    </Container>
  </div>
  )
};

export default CustomerEdit;
