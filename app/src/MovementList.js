import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Col, Container, Input, Label, Pagination, PaginationItem, PaginationLink, Row, Table, Tooltip } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

const MovementList = () => {

  const [movements, setMovements] = useState([]);
  const [loading, setLoading] = useState(false);
  const [tooltipOpen, setTooltipOpen] = useState(false);

  const [pageSize, setPageSize] = useState(5);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPage, setTotalPage] = useState(1);


  const toggle = () => setTooltipOpen(!tooltipOpen);

  useEffect(() => {
    setLoading(true);

    fetch(`api/movements?page=${currentPage}&size=${pageSize}`)
      .then(response => response.json())
      .then(data => {
        setMovements(data.movements);
        setTotalPage(data.page.totalPage);
        if (currentPage >= data.page.totalPage) setCurrentPage(data.page.totalPage - 1);
        setLoading(false);
      })

    setLoading(false);
  }, [currentPage, pageSize, totalPage]);

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


  const changePage = (e, i) => {
    setCurrentPage(i)
  }


  const handlePageSizeChange = (event) => {
    const newPageSize = parseInt(event.target.value); // Convert selected value to number
    setPageSize(newPageSize);
    // Trigger data fetch or other actions based on new page size (commented out)
  };

  if (loading) {
    return <p>Loading...</p>;
  }

  const movementList = movements.map(movement => {
    //const address = `${movement.address || ''} ${movement.city || ''} ${movement.stateOrProvince || ''}`;
    return <tr key={movement.id}>
      <td style={{ whiteSpace: 'nowrap' }}>{movement.id}</td>
      <td>{movement.movementType}</td>
      <td>
        {movement.movementType === 'DEBIT' ? (
          <span style={{ color: 'red', fontWeight: 'bold' }}>
            {movement.amount * -1}
          </span>
        ) : (
          <span style={{ color: 'green', fontWeight: 'bold' }}>
            {movement.amount}
          </span>
        )}
      </td>
      <td>{movement.currency}</td>
      <td>{movement.fromAccountId}</td>
      <td>{movement.toAccountId}</td>
      <td>{movement.fromSerialNumber}</td>
      <td>{movement.toSerialNumber}</td>
      <td>{movement.status}</td>

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
      <AppNavbar />
      <Container fluid>
        <div className="float-end">
          <Button color="success" tag={Link} to="/movements/new">Add movement</Button>
        </div>
        <h3>MOVEMENTS</h3>
        <Table className="mt-4">
          <thead>
            <tr>
              <th>id</th>
              <th>Movent type</th>
              <th>Amount</th>
              <th>Currency</th>
              <th>From account id</th>
              <th>To account id</th>
              <th>From  account serialNumber</th>
              <th>To  account serialNumber</th>
              <th>status</th>
              <th width="10%">Actions</th>
            </tr>
          </thead>
          <tbody>
            {movementList}
          </tbody>
        </Table>

        <div className="pagination-wrapper">

          <Row>
            <Col xs="1">
              <Label for="examplePassword">
                Page size
              </Label>
              <Input
                id="exampleSelect"
                name="select"
                type="select"
                value={pageSize}
                onChange={handlePageSizeChange}>
                <option value="5">5</option>
                <option value="10">10</option>
                <option value="20">20</option>
              </Input>
            </Col>
            <Col xs="11" md="11" className="d-flex align-items-center justify-content-center">

              <Pagination aria-label="Page navigation example">

                <PaginationItem disabled={currentPage <= 0}>

                  <PaginationLink
                    onClick={e => changePage(e, currentPage - 1)}
                    previous
                  />

                </PaginationItem>

                {loading ? (
                  <p>Loading pagination...</p>
                ) : (
                  [...Array(totalPage)].map((page, i) =>
                    <PaginationItem active={i === currentPage} key={i}>
                      <PaginationLink onClick={e => changePage(e, i)}>
                        {i + 1}
                      </PaginationLink>
                    </PaginationItem>
                  )
                )}

                <PaginationItem disabled={currentPage >= totalPage - 1}>

                  <PaginationLink
                    onClick={e => changePage(e, currentPage + 1)}
                    next
                  />

                </PaginationItem>

              </Pagination>

            </Col>
          </Row>

        </div>
      </Container>
    </div>
  );
};

export default MovementList;
