--USERS

-- you can user gen_random_uuid () to generate random IDs, use this only to generate testdata


insert into users (id, email,first_name,last_name, password)
values  ('ba804cb9-fa14-42a5-afaf-be488742fc54', 'admin@example.com', 'James','Bond', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6' ), -- Password: 1234
        ('0d8fa44c-54fd-4cd0-ace9-2a7da57992de', 'user@example.com', 'Tyler','Durden', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6'), -- Password: 1234
        ('31774049-72ca-4345-8dce-e27955485f52', 'default@example.com', 'Jamal','Wilson', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6') -- Password: 1234
 ON CONFLICT DO NOTHING;


--ROLES
INSERT INTO role(id, name)
VALUES ('d29e709c-0ff1-4f4c-a7ef-09f656c390f1', 'DEFAULT'),
('ab505c92-7280-49fd-a7de-258e618df074', 'ADMIN'),
('c6aee32d-8c35-4481-8b3e-a876a39b0c02', 'USER')
ON CONFLICT DO NOTHING;

--AUTHORITIES
INSERT INTO authority(id, name)
VALUES ('2ebf301e-6c61-4076-98e3-2a38b31daf86', 'USER_CREATE'),
       ('76d2cbf6-5845-470e-ad5f-2edb9e09a868', 'USER_READ'),
       ('21c942db-a275-43f8-bdd6-d048c21bf5ab', 'USER_DEACTIVATE'),
       ('0f6fcc30-ddba-4504-8c17-a3dc81e1f868', 'USER_MODIFY')
    ON CONFLICT DO NOTHING;

--assign roles to users
insert into users_role (users_id, role_id)
values ('ba804cb9-fa14-42a5-afaf-be488742fc54', 'd29e709c-0ff1-4f4c-a7ef-09f656c390f1'),
       ('0d8fa44c-54fd-4cd0-ace9-2a7da57992de', 'd29e709c-0ff1-4f4c-a7ef-09f656c390f1'),
       ('ba804cb9-fa14-42a5-afaf-be488742fc54', 'ab505c92-7280-49fd-a7de-258e618df074'),
       ('ba804cb9-fa14-42a5-afaf-be488742fc54', 'c6aee32d-8c35-4481-8b3e-a876a39b0c02'),
       ('31774049-72ca-4345-8dce-e27955485f52', 'd29e709c-0ff1-4f4c-a7ef-09f656c390f1')
 ON CONFLICT DO NOTHING;

--assign authorities to roles
INSERT INTO role_authority(role_id, authority_id)
VALUES ('d29e709c-0ff1-4f4c-a7ef-09f656c390f1', '2ebf301e-6c61-4076-98e3-2a38b31daf86'),
       ('ab505c92-7280-49fd-a7de-258e618df074', '76d2cbf6-5845-470e-ad5f-2edb9e09a868'),
       ('ab505c92-7280-49fd-a7de-258e618df074', '0f6fcc30-ddba-4504-8c17-a3dc81e1f868'),
       ('c6aee32d-8c35-4481-8b3e-a876a39b0c02', '21c942db-a275-43f8-bdd6-d048c21bf5ab')
    ON CONFLICT DO NOTHING;

--ENTRIES
-- ADDITIONAL ENTRIES (11 per user)
INSERT INTO list_entry(id, title, text, importance, user_id, created_at)
VALUES
    -- User: James Bond (ba804cb9-fa14-42a5-afaf-be488742fc54)
    (gen_random_uuid(), 'Skyfall Debrief', 'Submit the final field report regarding the Scottish Highlands operation.', 'HIGH', 'ba804cb9-fa14-42a5-afaf-be488742fc54', now()),
    (gen_random_uuid(), 'Walther PPK Maintenance', 'Routine cleaning and safety check of the service weapon.', 'MEDIUM', 'ba804cb9-fa14-42a5-afaf-be488742fc54', now()),
    (gen_random_uuid(), 'Omega Watch Service', 'Take the Seamaster to the armory for gadget calibration.', 'LOW', 'ba804cb9-fa14-42a5-afaf-be488742fc54', now()),
    (gen_random_uuid(), 'Contact Felix Leiter', 'Coordinate intelligence sharing regarding the Caribbean cartel.', 'HIGH', 'ba804cb9-fa14-42a5-afaf-be488742fc54', now()),
    (gen_random_uuid(), 'Aston Martin Refuel', 'Ensure the DB5 is ready for immediate deployment.', 'MEDIUM', 'ba804cb9-fa14-42a5-afaf-be488742fc54', now()),
    (gen_random_uuid(), 'MI6 HR Paperwork', 'Finalize the insurance forms for the damaged villa in Italy.', 'LOW', 'ba804cb9-fa14-42a5-afaf-be488742fc54', now()),
    (gen_random_uuid(), 'Casino Royale RSVP', 'Confirm attendance for the high-stakes poker tournament.', 'HIGH', 'ba804cb9-fa14-42a5-afaf-be488742fc54', now()),
    (gen_random_uuid(), 'Savile Row Tailor', 'Pick up the bespoke grey three-piece suit.', 'LOW', 'ba804cb9-fa14-42a5-afaf-be488742fc54', now()),
    (gen_random_uuid(), 'Secure Line Check', 'Verify the encryption protocols on the satellite phone.', 'MEDIUM', 'ba804cb9-fa14-42a5-afaf-be488742fc54', now()),
    (gen_random_uuid(), 'Vesper’s Anniversary', 'Quiet moment of reflection; visit the chapel.', 'LOW', 'ba804cb9-fa14-42a5-afaf-be488742fc54', now()),
    (gen_random_uuid(), 'Istanbul Safehouse', 'Verify the supply drop at the Bosphorus location.', 'HIGH', 'ba804cb9-fa14-42a5-afaf-be488742fc54', now()),

    -- User: Tyler Durden (0d8fa44c-54fd-4cd0-ace9-2a7da57992de)
    (gen_random_uuid(), 'Basement Cleanup', 'Scrub the floors after the Tuesday night session.', 'MEDIUM', '0d8fa44c-54fd-4cd0-ace9-2a7da57992de', now()),
    (gen_random_uuid(), 'Soap Packaging', 'Wrap the new shipment of luxury bars for the department store.', 'LOW', '0d8fa44c-54fd-4cd0-ace9-2a7da57992de', now()),
    (gen_random_uuid(), 'Recruitment Flyers', 'Print and distribute the "Are You Alive?" pamphlets.', 'HIGH', '0d8fa44c-54fd-4cd0-ace9-2a7da57992de', now()),
    ('2d21d79c-2bb7-4722-8e98-9c802b3958e1', 'Night Patrol', 'Walk the perimeter of the Paper St. property.', 'MEDIUM', '0d8fa44c-54fd-4cd0-ace9-2a7da57992de', now()),
    (gen_random_uuid(), 'Discard Belongings', 'Identify three more items in the house to throw away.', 'LOW', '0d8fa44c-54fd-4cd0-ace9-2a7da57992de', now()),
    (gen_random_uuid(), 'Fight Night Logistics', 'Ensure enough water and medical tape for the members.', 'HIGH', '0d8fa44c-54fd-4cd0-ace9-2a7da57992de', now()),
    (gen_random_uuid(), 'Propaganda Printing', 'Start the mimeograph for the Phase 2 instructions.', 'HIGH', '0d8fa44c-54fd-4cd0-ace9-2a7da57992de', now()),
    (gen_random_uuid(), 'Warehouse Rental', 'Renew the lease for the auxiliary storage space.', 'MEDIUM', '0d8fa44c-54fd-4cd0-ace9-2a7da57992de', now()),
    (gen_random_uuid(), 'Group Therapy Session', 'Attend the support group to find more "tourists".', 'LOW', '0d8fa44c-54fd-4cd0-ace9-2a7da57992de', now()),
    (gen_random_uuid(), 'Anonymous Donation', 'Drop off the confiscated corporate files at the news station.', 'HIGH', '0d8fa44c-54fd-4cd0-ace9-2a7da57992de', now()),
    (gen_random_uuid(), 'Reading the Manifesto', 'Review the core philosophy with the new Space Monkeys.', 'MEDIUM', '0d8fa44c-54fd-4cd0-ace9-2a7da57992de', now()),

    -- User: Jamal Wilson (31774049-72ca-4345-8dce-e27955485f52)
    (gen_random_uuid(), 'Sprint Planning', 'Determine the story points for the Q1 roadmap.', 'HIGH', '31774049-72ca-4345-8dce-e27955485f52', now()),
    (gen_random_uuid(), 'CI/CD Pipeline Fix', 'Debug why the Jenkins build is failing on the staging branch.', 'HIGH', '31774049-72ca-4345-8dce-e27955485f52', now()),
    (gen_random_uuid(), 'Code Review Feedback', 'Address the comments on the Postgres migration PR.', 'MEDIUM', '31774049-72ca-4345-8dce-e27955485f52', now()),
    (gen_random_uuid(), 'Grocery Delivery', 'Check the porch for the Whole Foods delivery.', 'LOW', '31774049-72ca-4345-8dce-e27955485f52', now()),
    (gen_random_uuid(), 'Unit Test Coverage', 'Increase the coverage for the service layer to 85%.', 'MEDIUM', '31774049-72ca-4345-8dce-e27955485f52', now()),
    (gen_random_uuid(), 'Cloud Provider Bill', 'Audit the AWS costs for the dev environment.', 'LOW', '31774049-72ca-4345-8dce-e27955485f52', now()),
    (gen_random_uuid(), 'Gym Membership', 'Renew the annual plan before the price increase.', 'LOW', '31774049-72ca-4345-8dce-e27955485f52', now()),
    (gen_random_uuid(), 'Tech Blog Post', 'Write 500 words about Spring Security best practices.', 'MEDIUM', '31774049-72ca-4345-8dce-e27955485f52', now()),
    (gen_random_uuid(), 'Database Migration', 'Run the Liquibase scripts on the production replica.', 'HIGH', '31774049-72ca-4345-8dce-e27955485f52', now()),
    ('fbfb502e-2989-441c-8374-c359905cad3f', 'Coffee Bean Refill', 'Order two bags of Ethiopian light roast.', 'LOW', '31774049-72ca-4345-8dce-e27955485f52', now()),
    (gen_random_uuid(), 'Weekend Hiking', 'Pack the gear for the trip to the Blue Ridge Mountains.', 'MEDIUM', '31774049-72ca-4345-8dce-e27955485f52', now())
    ON CONFLICT DO NOTHING;