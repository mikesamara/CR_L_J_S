SELECT ct.id,
       ct.name,
       p.subclass_name,
       ct.birthday,
       ct.commands
FROM cats ct
         LEFT JOIN pets p ON p.id = ct.type_id
UNION
SELECT dg.id,
       dg.name,
       p.subclass_name,
       dg.birthday,
       dg.commands
FROM dogs dg
         LEFT JOIN pets p ON p.id = dg.type_id
UNION
SELECT hm.id,
       hm.name,
       p.subclass_name,
       hm.birthday,
       hm.commands
FROM hamsters hm
         LEFT JOIN pets p ON p.id = hm.type_id
UNION
SELECT hr.id,
       hr.name,
       pa.subclass_name,
       hr.birthday,
       hr.commands
FROM horses hr
         LEFT JOIN pack_animals pa ON pa.id = hr.type_id
UNION
SELECT dk.id,
       dk.name,
       pa.subclass_name,
       dk.birthday,
       dk.commands
FROM donkeys dk
         LEFT JOIN pack_animals pa ON pa.id = dk.type_id
UNION
SELECT cm.id,
       cm.name,
       pa.subclass_name,
       cm.birthday,
       cm.commands
FROM camels cm
         LEFT JOIN pack_animals pa ON pa.id = cm.type_id