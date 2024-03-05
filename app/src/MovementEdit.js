import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { useCookies } from 'react-cookie';

const MovementEdit = () => {
  const initialFormState = {
    ownerId: 1,
    currencyCode: 'USD',
    balance: 0.1,
  };

  const [movement, setMovement] = useState(initialFormState);
  const navigate = useNavigate();
  const { id } = useParams();

  useEffect(() => {
    if (id !== 'new') {
      fetch(`/api/movements/${id}`)
        .then(response => response.json())
        .then(data => setMovement(data));
    }
  }, [id, setMovement]);

  const handleChange = (event) => {
    const { name, value } = event.target

    setMovement({ ...movement, [name]: value })
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
      credentials: 'include'
    });
    setMovement(initialFormState);
    navigate('/movements');
  }

  const title = <h2>{movement.id ? 'Edit Movement' : 'Add Movement'}</h2>;

  return (<div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={handleSubmit}>
          <FormGroup>
            <Label for="ownerId">Owner id</Label>
            <Input type="number" name="ownerId" id="ownerId" value={movement.ownerId || ''}
                   onChange={handleChange} autoComplete="ownerId"/>
          </FormGroup>
          <FormGroup>
            <Label for="currencyCode">Currency</Label>
            <Input type="text" name="currencyCode" id="currencyCode" value={movement.currencyCode || ''}
                   onChange={handleChange} autoComplete="currencyCode"/>
          </FormGroup>
          <FormGroup>
            <Label for="balance">Balance</Label>
            <Input type="number" name="balance" id="balance" value={movement.balance || ''}
                   onChange={handleChange} autoComplete="balance"/>
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
