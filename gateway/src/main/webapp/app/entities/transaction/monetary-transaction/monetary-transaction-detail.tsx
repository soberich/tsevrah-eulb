import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './monetary-transaction.reducer';
import { IMonetaryTransaction } from 'app/shared/model/transaction/monetary-transaction.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMonetaryTransactionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MonetaryTransactionDetail = (props: IMonetaryTransactionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { monetaryTransactionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gatewayApp.transactionMonetaryTransaction.detail.title">MonetaryTransaction</Translate> [
          <b>{monetaryTransactionEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="recipientID">
              <Translate contentKey="gatewayApp.transactionMonetaryTransaction.recipientID">Recipient ID</Translate>
            </span>
          </dt>
          <dd>{monetaryTransactionEntity.recipientID}</dd>
          <dt>
            <span id="senderID">
              <Translate contentKey="gatewayApp.transactionMonetaryTransaction.senderID">Sender ID</Translate>
            </span>
          </dt>
          <dd>{monetaryTransactionEntity.senderID}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="gatewayApp.transactionMonetaryTransaction.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{monetaryTransactionEntity.amount}</dd>
          <dt>
            <Translate contentKey="gatewayApp.transactionMonetaryTransaction.account">Account</Translate>
          </dt>
          <dd>{monetaryTransactionEntity.accountCustomerID ? monetaryTransactionEntity.accountCustomerID : ''}</dd>
        </dl>
        <Button tag={Link} to="/monetary-transaction" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/monetary-transaction/${monetaryTransactionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ monetaryTransaction }: IRootState) => ({
  monetaryTransactionEntity: monetaryTransaction.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MonetaryTransactionDetail);
