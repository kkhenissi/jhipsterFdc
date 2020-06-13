/*==============================================================*/
/* Nom de SGBD :  MySQL 5.0                                     */
/* Date de création :  07/04/2017 01:37:26                      */
/*==============================================================*/


drop table if exists AddressType;

drop table if exists Category;

drop table if exists City;

drop table if exists Claim;

drop table if exists Command;

drop table if exists Evaluation;

drop table if exists FideCoin;

drop table if exists Formula;

drop table if exists Merchant;

drop table if exists MerchantOrder;

drop table if exists MerchantType;

drop table if exists OrderLine;

drop table if exists Photo;

drop table if exists Product;

drop table if exists ProductItem;

drop table if exists Product_Formula;

drop table if exists Role;

drop table if exists Sale;

drop table if exists TraderOrder;

drop table if exists Tva;

drop table if exists TypePhone;

drop table if exists User;

drop table if exists UserAddress;

drop table if exists UserParticipantList;

drop table if exists UserPhone;

drop table if exists Day;

drop table if exists OpeningState;

drop table if exists Schedule;

drop table if exists TimeSlot;

/*==============================================================*/
/* Table : AddressType                                          */
/*==============================================================*/
create table AddressType
(
   idAddressType        int not null,
   nameAddressType      varchar(254),
   primary key (idAddressType)
);

/*==============================================================*/
/* Table : Category                                             */
/*==============================================================*/
create table Category
(
   idCategory           int not null,
   nameCategory         varchar(254),
   primary key (idCategory)
);

/*==============================================================*/
/* Table : City                                                 */
/*==============================================================*/
create table City
(
   idCity               int not null,
   nameCity             varchar(254) not null,
   postalCode           int,
   primary key (idCity)
);

/*==============================================================*/
/* Table : Claim                                                */
/*==============================================================*/
create table Claim
(
   idClaim              int not null,
   idUser               int not null,
   dateClaim            datetime,
   textClaim            varchar(254),
   primary key (idClaim)
);

/*==============================================================*/
/* Table : Command                                              */
/*==============================================================*/
create table Command
(
   idCommand            int not null,
   idUser               int not null,
   idMerchant           int not null,
   dateCommand          datetime,
   totalCommand         numeric(8,0),
   primary key (idCommand)
);

/*==============================================================*/
/* Table : Evaluation                                           */
/*==============================================================*/
create table Evaluation
(
   idEvaluation         int not null,
   idUser               int not null,
   idCommand            int not null,
   Tra_idCommand        int not null,
   dateEvaluation       datetime,
   noteEvaluation       int,
   textEvaluation       varchar(254),
   primary key (idEvaluation)
);

/*==============================================================*/
/* Table : FideCoin                                             */
/*==============================================================*/
create table FideCoin
(
   idFideCoin           numeric(8,0) not null,
   idUser               int not null,
   totalKeys            numeric(8,0),
   totalCoins           numeric(8,0),
   waitingKeys          numeric(8,0),
   waitingCoins         numeric(8,0),
   primary key (idFideCoin)
);

/*==============================================================*/
/* Table : Formula                                              */
/*==============================================================*/
create table Formula
(
   idFormula            int not null,
   nameFormula          varchar(254),
   priceFormula         numeric(8,0),
   primary key (idFormula)
);

/*==============================================================*/
/* Table : Merchant                                             */
/*==============================================================*/
create table Merchant
(
   idUser               int not null,
   idMerchant           int not null,
   idMerchantType       int not null,
   nameMerchant         varchar(254),
   deliveryCost         numeric(8,0),
   minOrderAmount       numeric(8,0),
   vacationDescription  varchar(254),
   companyName          varchar(254),
   legalStatus          varchar(254),
   sirenNumber          int,
   primary key (idUser, idMerchant)
);

/*==============================================================*/
/* Table : MerchantOrder                                        */
/*==============================================================*/
create table MerchantOrder
(
   idCommand            int not null,
   idUser               int not null,
   idMerchantOrder      int,
   winKeys              numeric(8,0),
   winCoins             numeric(8,0),
   spentCoins           int,
   deliveryStatus       varchar(254),
   primary key (idCommand)
);

/*==============================================================*/
/* Table : MerchantType                                         */
/*==============================================================*/
create table MerchantType
(
   idMerchantType       int not null,
   merchantType         varchar(254),
   primary key (idMerchantType)
);

/*==============================================================*/
/* Table : OrderLine                                            */
/*==============================================================*/
create table OrderLine
(
   idOrderLine          int not null,
   idCategory           int not null,
   idProduct            int not null,
   idCommand            int not null,
   quantityOrderLine    int,
   sumOrderLine         numeric(8,0),
   nameProduct          varchar(254),
   priceProduct         numeric(8,0),
   descriptionProduct   varchar(254),
   valueTva             numeric(8,0),
   primary key (idOrderLine)
);

/*==============================================================*/
/* Table : Photo                                                */
/*==============================================================*/
create table Photo
(
   idPhoto              int not null,
   idUser               int not null,
   idCategory           int not null,
   idProduct            int not null,
   namePhoto            varchar(254),
   descriptionPhoto     varchar(254),
   primary key (idPhoto)
);

/*==============================================================*/
/* Table : Product                                              */
/*==============================================================*/
create table Product
(
   idCategory           int not null,
   idProduct            int not null,
   idUser               int not null,
   idMerchant           int not null,
   idTva                int not null,
   nameProduct          varchar(254),
   descriptionProduct   varchar(254),
   priceProduct         numeric(8,0),
   primary key (idCategory, idProduct)
);

/*==============================================================*/
/* Table : ProductItem                                          */
/*==============================================================*/
create table ProductItem
(
   idCategory           int not null,
   idProduct            int not null,
   idProductItem        int not null,
   quantityItem         int,
   primary key (idCategory, idProduct, idProductItem)
);

/*==============================================================*/
/* Table : Product_Formula                                      */
/*==============================================================*/
create table Product_Formula
(
   idFormula            int not null,
   idCategory           int not null,
   idProduct            int not null,
   primary key (idCategory, idFormula, idProduct)
);

/*==============================================================*/
/* Table : Role                                                 */
/*==============================================================*/
create table Role
(
   idRole               int not null,
   typeRole             varchar(254),
   primary key (idRole)
);

/*==============================================================*/
/* Table : Sale                                                 */
/*==============================================================*/
create table Sale
(
   idSale               int not null,
   idCategory           int not null,
   idProduct            int not null,
   idProductItem        int not null,
   idUser               int not null,
   idMerchant           int not null,
   idCommand            int,
   offeringDateSale     datetime,
   statusSale           int,
   currentPrice         numeric(8,0),
   dateSale             datetime,
   totalKeysSale        int,
   endedSale            bool,
   primary key (idSale)
);

/*==============================================================*/
/* Table : TraderOrder                                          */
/*==============================================================*/
create table TraderOrder
(
   idCommand            int not null,
   idSale               int not null,
   idTraderOrder        int,
   spentKeysUser        int,
   totalKeys            int,
   primary key (idCommand)
);

/*==============================================================*/
/* Table : Tva                                                  */
/*==============================================================*/
create table Tva
(
   idTva                int not null,
   valueTva             numeric(8,0),
   primary key (idTva)
);

/*==============================================================*/
/* Table : TypePhone                                            */
/*==============================================================*/
create table TypePhone
(
   idTypePhone          int not null,
   typePhone            varchar(254),
   primary key (idTypePhone)
);

/*==============================================================*/
/* Table : User                                                 */
/*==============================================================*/
create table User
(
   idUser               int not null,
   idRole               int not null,
   userNameUser         varchar(254),
   firstNameUser        varchar(254),
   lastNameUser         varchar(254),
   emailUser            varchar(254),
   passwordUser         varchar(254),
   dateRegistrationUser datetime,
   avatarUser           varchar(254),
   statusUser           bool,
   primary key (idUser)
);

/*==============================================================*/
/* Table : UserAddress                                          */
/*==============================================================*/
create table UserAddress
(
   idUserAddress        int not null,
   idCity               int not null,
   idUser               int not null,
   idAddressType        int not null,
   userAddress          varchar(254),
   additionalAddress    varchar(254),
   primary key (idUserAddress)
);

/*==============================================================*/
/* Table : UserParticipantList                                  */
/*==============================================================*/
create table UserParticipantList
(
   idUser               int not null,
   idSale               int not null,
   primary key (idUser, idSale)
);

/*==============================================================*/
/* Table : UserPhone                                            */
/*==============================================================*/
create table UserPhone
(
   idPhone              int not null,
   idTypePhone          int not null,
   idUser               int not null,
   numberPhone          int,
   primary key (idPhone)
);
/*==============================================================*/
/* Table : Schedule                                             */
/*==============================================================*/
create table Schedule
(
   idSchedule           int not null,
   idOpeningState       int not null,
   idUser               int,
   idMerchant           int,
   descriptionSchedule  varchar(254)
);

/*==============================================================*/
/* Table : TimeSlot                                             */
/*==============================================================*/
create table TimeSlot
(
   idTimeSlot           int not null,
   idSchedule           int not null,
   startTimeSlot        datetime,
   endTimeSlot          datetime
);

/*==============================================================*/
/* Table : Day                                                  */
/*==============================================================*/
create table Day
(
   idDay                int not null,
   idSchedule           int,
   nameDay              varchar(254)
);
/*==============================================================*/
/* Table : OpeningState                                         */
/*==============================================================*/
create table OpeningState
(
   idOpeningState       int not null,
   idSchedule           int not null,
   nameOpeningState     varchar(254)
);

alter table Claim add constraint FK_User_Ds_Claim foreign key (idUser)
      references User (idUser) on delete restrict on update restrict;

alter table Command add constraint FK_Merchand_Ds_Commande foreign key (idUser, idMerchant)
      references Merchant (idUser, idMerchant) on delete restrict on update restrict;

alter table Evaluation add constraint FK_MerchantOrder_Ds_Evaluation foreign key (idCommand)
      references MerchantOrder (idCommand) on delete restrict on update restrict;

alter table Evaluation add constraint FK_TradeOrder_Ds_Evaluation foreign key (Tra_idCommand)
      references TraderOrder (idCommand) on delete restrict on update restrict;

alter table Evaluation add constraint FK_User_Ds_Evaluation foreign key (idUser)
      references User (idUser) on delete restrict on update restrict;

alter table FideCoin add constraint FK_User_Ds_FideCoin foreign key (idUser)
      references User (idUser) on delete restrict on update restrict;

alter table Merchant add constraint FK_GeneralisationMerchantHeriteUser foreign key (idUser)
      references User (idUser) on delete restrict on update restrict;

alter table Merchant add constraint FK_MerchantType_Ds_Merchant foreign key (idMerchantType)
      references MerchantType (idMerchantType) on delete restrict on update restrict;

alter table MerchantOrder add constraint FK_MerchantOrderHeriteCommande foreign key (idCommand)
      references Command (idCommand) on delete restrict on update restrict;

alter table MerchantOrder add constraint FK_User_MerchandOrder foreign key (idUser)
      references User (idUser) on delete restrict on update restrict;

alter table OrderLine add constraint FK_MerchantOrder_Ds_OrderLine foreign key (idCommand)
      references MerchantOrder (idCommand) on delete restrict on update restrict;

alter table OrderLine add constraint FK_Product_Ds_OrderLine foreign key (idCategory, idProduct)
      references Product (idCategory, idProduct) on delete restrict on update restrict;

alter table Photo add constraint FK_Product_Ds_Photo foreign key (idCategory, idProduct)
      references Product (idCategory, idProduct) on delete restrict on update restrict;

alter table Photo add constraint FK_User_Ds_Photo foreign key (idUser)
      references User (idUser) on delete restrict on update restrict;

alter table Product add constraint FK_Category_Ds_Product foreign key (idCategory)
      references Category (idCategory) on delete restrict on update restrict;

alter table Product add constraint FK_Merchant_Ds_Product foreign key (idUser, idMerchant)
      references Merchant (idUser, idMerchant) on delete restrict on update restrict;

alter table Product add constraint FK_Tva_Ds_Product foreign key (idTva)
      references Tva (idTva) on delete restrict on update restrict;

alter table ProductItem add constraint FK_Generalisation_ProductItemHeriteProduct foreign key (idCategory, idProduct)
      references Product (idCategory, idProduct) on delete restrict on update restrict;

alter table Product_Formula add constraint FK_Product_Formula1 foreign key (idFormula)
      references Formula (idFormula) on delete restrict on update restrict;

alter table Product_Formula add constraint FK_Product_Formula2 foreign key (idCategory, idProduct)
      references Product (idCategory, idProduct) on delete restrict on update restrict;

alter table Sale add constraint FK_Merchand_Ds_Sale foreign key (idUser, idMerchant)
      references Merchant (idUser, idMerchant) on delete restrict on update restrict;

alter table Sale add constraint FK_ProductItem_Ds_Sale foreign key (idCategory, idProduct, idProductItem)
      references ProductItem (idCategory, idProduct, idProductItem) on delete restrict on update restrict;

alter table Sale add constraint FK_Sale_Ds_TraderOrder1 foreign key (idCommand)
      references TraderOrder (idCommand) on delete restrict on update restrict;

alter table TraderOrder add constraint FK_Generalisation_TraderOrderHeriteCommande foreign key (idCommand)
      references Command (idCommand) on delete restrict on update restrict;

alter table TraderOrder add constraint FK_Sale_Ds_TraderOrder2 foreign key (idSale)
      references Sale (idSale) on delete restrict on update restrict;

alter table User add constraint FK_Role_Ds_User foreign key (idRole)
      references Role (idRole) on delete restrict on update restrict;

alter table UserAddress add constraint FK_AdresseType_Ds_UserAdresse foreign key (idAddressType)
      references AddressType (idAddressType) on delete restrict on update restrict;

alter table UserAddress add constraint FK_City_Ds_UserAdresse foreign key (idCity)
      references City (idCity) on delete restrict on update restrict;

alter table UserAddress add constraint FK_User_Ds_UserAdresse foreign key (idUser)
      references User (idUser) on delete restrict on update restrict;

alter table UserParticipantList add constraint FK_UserParticipantList1 foreign key (idUser)
      references User (idUser) on delete restrict on update restrict;

alter table UserParticipantList add constraint FK_UserParticipantList2 foreign key (idSale)
      references Sale (idSale) on delete restrict on update restrict;

alter table UserPhone add constraint FK_TypePhone_Ds_UserPhone foreign key (idTypePhone)
      references TypePhone (idTypePhone) on delete restrict on update restrict;

alter table UserPhone add constraint FK_User_Ds_UserPhone foreign key (idUser)
      references User (idUser) on delete restrict on update restrict;

