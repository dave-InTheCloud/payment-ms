import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Alert, Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { useCookies } from 'react-cookie';
import formatErrorMessage from './ErrorUtils';

const MovementEdit = () => {
  const initialFormState = {
    fromAccountId: '',
    toAccountId: '',
    fromSerialNumber: '',
    toSerialNumber: '',
    amount: 0,
  };

  const [movement, setMovement] = useState(initialFormState);
  const navigate = useNavigate();
  const { id } = useParams();
  const [error, setError] = useState(null);

  useEffect(() => {
    if (id !== 'new') {
      fetch(`/api/movements/${id}`)
        .then(response => response.json())
        .then(data => setMovement(data))
    }
  }, [id, setMovement]);

  const handleChange = (event) => {
    const { name, value } = event.target
    debugger
    if (name === 'fromAccountId') {
      // Handle both fromAccountId and toAccountId simultaneously
      setMovement({
        ...movement,
        [name]: !isNaN(parseInt(value)) ? parseInt(value) : null, // Set to either integer or original value
        ['fromSerialNumber']: value, // Clear fromSerialNumber if either account ID changes
      });
    } else if (name === 'toAccountId') {
      setMovement({
        ...movement,
        [name]: !isNaN(parseInt(value)) ? parseInt(value) : null, // Set to either integer or original value
        ['toSerialNumber']: value,   // Clear toSerialNumber for consistency
      });
    } else {
      // Handle other form fields as usual
      setMovement({ ...movement, [name]: value });
    }
    console.log("movement before sending request:", movement);

  }

  const handleSubmit = async (event) => {
    event.preventDefault();

    await fetch(`/api/movements${movement.id ? `/${movement.id}` : ''}`, {
      method: (movement.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(movement),
    }).then(response => {
      if (!response.ok) {
        throw response.clone();
      }

      setMovement(initialFormState);
      navigate('/movements');
    }).catch((error) => {
      formatErrorMessage(error, setError);
    });
  }

  const title = <h2>{movement.id ? 'Edit Movement' : 'Add Movement'}</h2>;

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
          <Label for="fromAccountId">Source Account</Label>
          <Input type="text" name="fromAccountId" id="fromAccountId" value={movement.fromAccountId || movement.fromSerialNumber || ''} placeholder='Account ID or serial number'
            onChange={handleChange} autoComplete="fromAccountId" />
        </FormGroup>
        <FormGroup>
          <Label for="toAccountId">Destination Account</Label>
          <Input type="text" name="toAccountId" id="toAccountId" value={movement.toAccountId || movement.toSerialNumber || ''} placeholder='Account ID or serial number'
            onChange={handleChange} autoComplete="toAccountId" />
        </FormGroup>

        <FormGroup>
          <Label for="amount">Amount</Label>
          <Input type="number" name="amount" id="amount" value={movement.amount || ''} placeholder='Amount greater then 0'
            onChange={handleChange} autoComplete="amount" />
        </FormGroup>
        {/* <div className="row">
            <FormGroup className="col-md-4 mb-3">
              <Label for="stateOrProvince">State/Province</Label>
              <Input type="text" name="stateOrProvince" id="stateOrProvince" value={movement.stateOrProvince || ''}
                     onChange={handleChange} autoComplete="address-level1"/>
            </FormGroup>
            <FormGroup className="col-md-5 mb-3">
              <Label for="country">Country</Label>
              <Input type="text" name="country" id="country" value={movement.country || ''}
                     onChange={handleChange} autoComplete="address-level1"/>
            </FormGroup>
            <FormGroup className="col-md-3 mb-3">
              <Label for="country">Postal Code</Label>
              <Input type="text" name="postalCode" id="postalCode" value={movement.postalCode || ''}
                     onChange={handleChange} autoComplete="address-level1"/>
            </FormGroup>
          </div> */}
        <FormGroup>
          <Button color="primary" type="submit">Save</Button>{' '}
          <Button color="secondary" tag={Link} to="/movements">Cancel</Button>
        </FormGroup>
      </Form>
    </Container>
  </div>
  )
};

export default MovementEdit;
