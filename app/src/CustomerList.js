import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Container, Row, Col, Input, Pagination, PaginationItem, PaginationLink, Table, Tooltip, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import { useCookies } from 'react-cookie';

const CustomerList = () => {

  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(false);

  const [pageSize, setPageSize] = useState(5);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPage, setTotalPage] = useState(1);

  const [tooltipOpen, setTooltipOpen] = useState(false);

  const toggle = () => setTooltipOpen(!tooltipOpen);

  useEffect(() => {

    setLoading(true);

    fetch(`api/customers?page=${currentPage}&size=${pageSize}`)
      .then(response => response.json())
      .then(data => {
        setCustomers(data.customers);
        setTotalPage(data.page.totalPage);
        setLoading(false);
      })

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
      let updatedCustomers = [...customers].filter(i => i.id !== id);
      setCustomers(updatedCustomers);
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

  // Join IDs with comma separator

  const customerList = customers.map(customer => {
    const allIds = customer.accounts.map((account) => account.id).join(', ');

return <tr key={customer.id}>
      <td style={{ whiteSpace: 'nowrap' }}>{customer.id}</td>
      <td>{customer.name}</td>
      <td>{customer.email}</td>
      <td>{customer.dateOfBirth}</td>
      <td>{allIds}</td>
      <td>
        <ButtonGroup id="action">
          <Button size="sm" color="primary" tag={Link} to={"/customers/" + customer.id} disabled>Edit</Button>
          <Button size="sm" color="danger" onClick={() => remove(customer.id)} disabled>Delete</Button>
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
          <Button color="success" tag={Link} to="/customers/new">Add customer</Button>
        </div>
        <h3>CUSTOMERS</h3>
        <Table className="mt-4">
          <thead>
            <tr>
              <th width="20%">id</th>
              <th width="20%">name</th>
              <th>Email</th>
              <th>Date Of Birth</th>
              <th>Accounts ids</th>
              <th width="10%">Actions</th>
            </tr>
          </thead>
          <tbody>
            {customerList}
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

export default CustomerList;
