import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Alert, Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import formatErrorMessage from './ErrorUtils';

const AccountEdit = () => {
  const initialFormState = {
    ownerId: 1,
    currencyCode: 'USD',
    balance: 0.1,
  };

  const [account, setAccount] = useState(initialFormState);
  const navigate = useNavigate();
  const { id } = useParams();
  const [error, setError] = useState(null);

  useEffect(() => {
    if (id !== 'new') {
      fetch(`/api/accounts/${id}`)
        .then(response => response.json())
        .then(data => setAccount(data))
       
    }
  }, [id, setAccount]);

  const handleChange = (event) => {
    const { name, value } = event.target

    setAccount({ ...account, [name]: value })
  }

  const handleSubmit = async (event) => {
    event.preventDefault();

    await fetch(`/api/accounts${account.id ? `/${account.id}` : ''}`, {
      method: (account.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(account),
    }) .then(response => {
      if (!response.ok) {
        throw response.clone();
      }
      setAccount(initialFormState);
      navigate('/accounts');
    }).catch((error) => {
      formatErrorMessage(error, setError);
    });
  }

  const title = <h2>{account.id ? 'Edit Account' : 'Add Account'}</h2>;

  return (<div>
      <AppNavbar/>
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
            <Label for="ownerId">Owner id</Label>
            <Input type="number" name="ownerId" id="ownerId" value={account.ownerId || ''}
                   onChange={handleChange} autoComplete="ownerId"/>
          </FormGroup>
          <FormGroup>
            <Label for="currencyCode">Currency</Label>
            <Input type="text" name="currencyCode" id="currencyCode" value={account.currencyCode || ''}
                   onChange={handleChange} autoComplete="currencyCode"/>
          </FormGroup>
          <FormGroup>
            <Label for="balance">Balance</Label>
            <Input type="number" name="balance" id="balance" value={account.balance || ''}
                   onChange={handleChange} autoComplete="balance"/>
          </FormGroup>
          {/* <div className="row">
            <FormGroup className="col-md-4 mb-3">
              <Label for="stateOrProvince">State/Province</Label>
              <Input type="text" name="stateOrProvince" id="stateOrProvince" value={account.stateOrProvince || ''}
                     onChange={handleChange} autoComplete="address-level1"/>
            </FormGroup>
            <FormGroup className="col-md-5 mb-3">
              <Label for="country">Country</Label>
              <Input type="text" name="country" id="country" value={account.country || ''}
                     onChange={handleChange} autoComplete="address-level1"/>
            </FormGroup>
            <FormGroup className="col-md-3 mb-3">
              <Label for="country">Postal Code</Label>
              <Input type="text" name="postalCode" id="postalCode" value={account.postalCode || ''}
                     onChange={handleChange} autoComplete="address-level1"/>
            </FormGroup>
          </div> */}
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to="/accounts">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  )
};

export default AccountEdit;
