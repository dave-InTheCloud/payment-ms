import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Container, Table, Tooltip } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import { useCookies } from 'react-cookie';

const MovementList = () => {

  const [movements, setMovements] = useState([]);
  const [loading, setLoading] = useState(false);
  const [tooltipOpen, setTooltipOpen] = useState(false);

  const toggle = () => setTooltipOpen(!tooltipOpen);

  useEffect(() => {
    setLoading(true);

  /*   fetch('api/movements')
      .then(response => response.json())
      .then(data => {
        setMovements(data);
        setLoading(false);
      }) */

      setLoading(false);
  }, []);

  const remove = async (id) => {
    await fetch(`/api/group/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      credentials: 'include'
    }).then(() => {
      let updatedMovements = [...movements].filter(i => i.id !== id);
      setMovements(updatedMovements);
    });
  }

  if (loading) {
    return <p>Loading...</p>;
  }

  const movementList = movements.map(movement => {
    //const address = `${movement.address || ''} ${movement.city || ''} ${movement.stateOrProvince || ''}`;
    return <tr key={movement.id}>
      <td style={{ whiteSpace: 'nowrap' }}>{movement.name}</td>
      <td>{movement.serialNumber}</td>
      <td>{movement.type}</td>
      <td>{movement.currencyCode}</td>
      <td>{movement.balance}</td>
      {/* <td>{movement.events.map(event => {
        return <div key={event.id}>{new Intl.DateTimeFormat('en-US', {
          year: 'numeric',
          month: 'long',
          day: '2-digit'
        }).format(new Date(event.date))}: {event.title}</div>
      })}</td> */}
      <td>
        <ButtonGroup id="action">
          <Button size="sm" color="primary" tag={Link} to={"/movements/" + movement.id} disabled>Edit</Button>
          <Button size="sm" color="danger" onClick={() => remove(movement.id)} disabled>Delete</Button>
        </ButtonGroup>

        <Tooltip
           placement={'left'}
        isOpen={tooltipOpen}
        target={'action'}
        toggle={toggle}
          > Not yet implemented</Tooltip>
      </td>
    </tr>
  });

  return (
    <div>
      <AppNavbar/>
      <Container fluid>
        <div className="float-end">
          <Button color="success" tag={Link} to="/movements/new">Add movement</Button>
        </div>
        <h3>MOVEMENTS</h3>
        <Table className="mt-4">
          <thead>
          <tr>
            <th width="20%">id</th>
            <th width="20%">serialNumber</th>
            <th>Type</th>
            <th>Currency</th>
            <th>Balance</th>
            <th width="10%">Actions</th>
          </tr>
          </thead>
          <tbody>
          {movementList}
          </tbody>
        </Table>
      </Container>
    </div>
  );
};

export default MovementList;
